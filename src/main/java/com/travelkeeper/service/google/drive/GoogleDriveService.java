package com.travelkeeper.service.google.drive;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Base service
 *
 * Created by netocris on 24/08/2018
 */
@Slf4j
class GoogleDriveService implements IGoogleDriveService {

    private final Environment env;

    GoogleDriveService(final Environment env) {
        this.env = env;
    }

    private Drive getDriveService() throws IOException, GeneralSecurityException, URISyntaxException {

        final String accountKey = this.env.getProperty("google.service-account-key");
        if(StringUtils.isEmpty(accountKey)){
            throw new IllegalArgumentException("Google Drive service account key is missing!");
        }

        final URL resource = new URL(accountKey);
        final java.io.File key = Paths.get(resource.toURI()).toFile();
        final NetHttpTransport httpTransport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = new JacksonFactory();

        final GoogleCredential credentials = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(jacksonFactory)
                .setServiceAccountScopes(Collections.singleton(DriveScopes.DRIVE))
                .setServiceAccountPrivateKeyFromP12File(key)
                .build();

        final String appName = this.env.getProperty("google.application-name");
        if(StringUtils.isEmpty(appName)){
            throw new IllegalArgumentException("Google Drive service application name is missing!");
        }

        return new Drive.Builder(httpTransport, jacksonFactory, credentials)
                .setApplicationName(appName)
                .setHttpRequestInitializer(credentials)
                .build();

    }

//    @Override
//    public File download(String file) {
//
//        try {
//            final Drive driveService = GoogleDriveUtils.getDriveService();
//            if(driveService != null){
//
//                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                final Drive.Files.Get item = driveService.files().get(file);
//                item.executeMediaAndDownloadTo(outputStream);
//
//                final File downloadedFile = new File(file);
//                FileCopyUtils.copy(outputStream.toByteArray(), downloadedFile);
//                return downloadedFile;
//            }
//        } catch (IOException ex) {
//            log.error("Error", ex);
//        } catch (GeneralSecurityException ex) {
//            log.error("Error", ex);
//        }
//
//        return null;
//    }

    @Override
    public File upload(String path, String name, String mimeType) {

        try {

            final String folderId = this.env.getProperty("google.folder-id");
            if(StringUtils.isEmpty(folderId)){
                throw new IllegalArgumentException("Google Drive service folder id is missing!");
            }

            final java.io.File fileUpload = new java.io.File(path);
            final File metadata = new File();
            metadata.setMimeType(mimeType);
            metadata.setName(name);
            metadata.setParents(Collections.singletonList(folderId));

            final FileContent fileContent = new FileContent(mimeType, fileUpload);

            return getDriveService().files().create(metadata, fileContent)
                    .setFields("id,webContentLink,webViewLink")
                    .execute();

        } catch (IOException ex) {
            log.error("Error", ex);
        } catch (GeneralSecurityException ex) {
            log.error("Error", ex);
        } catch (URISyntaxException ex) {
            log.error("Error", ex);
        }


        return null;

//        try {
//            final Drive driveService = GoogleDriveUtils.getDriveService();
//            if(driveService != null){
//
//                final com.google.api.services.drive.model.File file1 =
//                        new com.google.api.services.drive.model.File();
//                driveService.files().create(file1);
//            }
//        } catch (IOException ex) {
//            log.error("Error", ex);
//        } catch (GeneralSecurityException ex) {
//            log.error("Error", ex);
//        }

    }



}
