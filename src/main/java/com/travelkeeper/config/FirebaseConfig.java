package com.travelkeeper.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Firebase configuration
 *
 * Created by netocris on 21/09/2018
 */
@Configuration
@Slf4j
public class FirebaseConfig {

    private final Environment env;

    FirebaseConfig(final Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void init(){

        try {

            final FileInputStream in = new FileInputStream(
                    ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX)
                            + getProperty("firebase.credentials-path"));
            final FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(in))
                    .setDatabaseUrl(getProperty("firebase.database.url"))
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (IOException ex) {
            log.debug("Error", ex);
        }

    }

    @Bean
    public DatabaseReference getDatabase(){
        return FirebaseDatabase.getInstance().getReference();
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
