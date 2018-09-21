package com.travelkeeper.config;

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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 * Google Drive configuration
 *
 *  Created by netocris on 21/09/2018
 */
@Configuration
@Slf4j
public class GoogleDriveConfig {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final Environment env;

    GoogleDriveConfig(final Environment env) {
        this.env = env;
    }

    /**
     * Create Drive instance
     *
     * @return Drive
     */
    @Bean
    public Drive getDriveService() {

        try {

            // Build a new authorized API client service.
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            // Read client_secret.json file & create Credential object
            final Credential credentials = getCredentials(httpTransport);

            // Create Google Drive Service.
            return new Drive.Builder(httpTransport, JSON_FACTORY, credentials)
                    .setApplicationName("")
                    .build();

        } catch (IOException | GeneralSecurityException ex) {
            log.error("Error", ex);
        }

        return null;

    }

    /**
     * Create credentials to access google drive service
     *
     * @param httpTransport http transport
     *
     * @return Credentials
     *
     * @throws IOException IOException some IOException
     */
    private Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {

        final java.io.File credentials = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX);
        final String clientSecret = getProperty("google.credentials-path");
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

    /**
     * Load resource property
     *
     * @param key resource key
     * @return resource value
     */
    private String getProperty(final String key){

        final String value = this.env.getProperty(key);
        if(StringUtils.isEmpty(value)){
            throw new IllegalArgumentException("Resource key " + key + " value is missing!");
        }

        return value;

    }

}
