package com.travelkeeper.service.google.drive;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

/**
 * Base service
 *
 * Created by netocris on 24/08/2018
 */
@Service
@Slf4j
class GoogleDriveService implements IGoogleDriveService {

//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private Drive service;

    @Autowired
    public GoogleDriveService(final Drive service){
        this.service = service;
    }

    @Override
    public byte[] download(String filename) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            this.service.files()
                    .get(filename)
                    .executeAndDownloadTo(outputStream);

            return outputStream.toByteArray();

        } catch (IOException ex) {
            log.error("Error", ex);
        }

        return null;
    }

    @Override
    public File upload(String path, String mimeType, String filename, byte[] content) {

        try {

            final File metadata = new File();
            metadata.setName(filename);
            metadata.setParents(Collections.singletonList(path));

            return this.service.files()
                    .create(metadata, new ByteArrayContent(mimeType, content))
                    .setFields("id,webContentLink,webViewLink")
                    .execute();

        } catch (IOException ex) {
            log.error("Error", ex);
        }

        return null;

    }

//    /**
//     * Create Drive instance
//     *
//     * @return Drive
//     *
//     * @throws IOException some IOException
//     * @throws GeneralSecurityException some GeneralSecurityException
//     */
//    private Drive getDriveService() throws IOException, GeneralSecurityException {
//
//        // Build a new authorized API client service.
//        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//
//        // Read client_secret.json file & create Credential object
//        final Credential credentials = getCredentials(httpTransport);
//
//        // Create Google Drive Service.
//        return new Drive.Builder(httpTransport, JSON_FACTORY, credentials)
//                .setApplicationName("")
//                .build();
//
//    }
//
//    /**
//     * Create credentials
//     *
//     * @param httpTransport http transport
//     *
//     * @return Credentials
//     *
//     * @throws IOException IOException some IOException
//     */
//    private static Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
//
//        final java.io.File credentials = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX);
//        final String clientSecret = "credentials/client_secret.json";
//        final java.io.File clientSecretFilePath = new java.io.File(credentials, clientSecret);
//        if(!clientSecretFilePath.exists()){
//            throw new FileNotFoundException("Please copy " + clientSecret
//                    + " to folder: " + credentials.getAbsolutePath());
//        }
//
//        // load client secrets
//        final FileInputStream in = new FileInputStream(clientSecretFilePath);
//        final GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
//                JSON_FACTORY, new InputStreamReader(in));
//
//        // build flow and trigger user authorization request
//        final List<String> scopes = Collections.singletonList(DriveScopes.DRIVE);
//        final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
//                .Builder(httpTransport, JSON_FACTORY, clientSecrets, scopes)
//                .setDataStoreFactory(new FileDataStoreFactory(credentials))
//                .setAccessType("offline")
//                .build();
//
//        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
//                .authorize("user");
//
//    }

}
