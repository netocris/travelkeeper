package com.travelkeeper.service.google.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleDriveUtils {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     *
     * @return
     */
    public static Drive getDriveService() throws IOException, GeneralSecurityException {

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

        final File credentials = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "credentials.json");
        //final File credentials = new File(System.getProperty("user.home"), "credentials");
        final String clientSecret = "client_secret.json";
        final File clientSecretFilePath = new File(credentials, clientSecret);
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
