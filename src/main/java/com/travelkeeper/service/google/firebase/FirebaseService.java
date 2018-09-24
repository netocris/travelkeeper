package com.travelkeeper.service.google.firebase;

import com.google.firebase.database.DatabaseReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of service to interact with Google Firebase API
 *
 * Created by netocris on 21/09/2018
 */
@Service
@Slf4j
public class FirebaseService implements IFirebaseService {

    private DatabaseReference databaseReference;

    @Autowired
    public FirebaseService(final DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    @Override
    public String getAll() {
        return null;
    }

    @Override
    public void save(String value) {

    }

}
