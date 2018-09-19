package com.travelkeeper.service.google.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 * Base service
 *
 * Created by netocris on 24/08/2018
 */
@Slf4j
class GoogleDriveService implements IGoogleDriveService {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final Environment env;

    GoogleDriveService(final Environment env) {
        this.env = env;
    }

    @Override
    public byte[] download(String file) {

        try {
            final Drive driveService = getDriveService();
            if(driveService != null){
                OutputStream outputStream = new ByteArrayOutputStream();
                driveService.files()
                        .get(file)
                        .executeAndDownloadTo(outputStream);
                return ((ByteArrayOutputStream) outputStream).toByteArray();
            }
        } catch (IOException ex) {
            log.error("Error", ex);
        } catch (GeneralSecurityException ex) {
            log.error("Error", ex);
        }

        return null;
    }

    @Override
    public File upload(String path, String name, String mimeType) {

        try {
            final Drive driveService = getDriveService();
            if(driveService != null){

                final String folderId = this.env.getProperty("google.folder");
                if(StringUtils.isEmpty(folderId)){
                    throw new IllegalArgumentException("Google Drive service folder id is missing!");
                }

                final java.io.File fileUpload = new java.io.File(path);
                final File metadata = new File();
                metadata.setName(name);
                metadata.setParents(Collections.singletonList(folderId));

                final FileContent fileContent = new FileContent(mimeType, fileUpload);

                return driveService.files()
                        .create(metadata, fileContent)
                        .setFields("id,webContentLink,webViewLink")
                        .execute();
            }
        } catch (IOException ex) {
            log.error("Error", ex);
        } catch (GeneralSecurityException ex) {
            log.error("Error", ex);
        }

        return null;

    }

    private Drive getDriveService() throws IOException, GeneralSecurityException {

        // Build a new authorized API client service.
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        // Read client_secret.json file & create Credential object
        final Credential credentials = getCredentials(httpTransport);

        // Create Google Drive Service.
        return new Drive.Builder(httpTransport, JSON_FACTORY, credentials)
                .setApplicationName("")
                .build();

    }

    /**
     *
     * @param httpTransport
     *
     * @return
     *
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private static Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {

        final java.io.File credentials = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "credentials.json");
        final String clientSecret = "client_secret.json";
        final java.io.File clientSecretFilePath = new java.io.File(credentials, clientSecret);
        if(!clientSecretFilePath.exists()){
            throw new FileNotFoundException("Please copy " + clientSecret
                    + " to folder: " + credentials.getAbsolutePath());
        }

        // load client secrets
        final FileInputStream in = new FileInputStream(clientSecretFilePath);
        final GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, new InputStreamReader(in));

        // build flow and trigger user authorization request
        final List<String> scopes = Collections.singletonList(DriveScopes.DRIVE);
        final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                .Builder(httpTransport, JSON_FACTORY, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(credentials))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                .authorize("user");

    }

}