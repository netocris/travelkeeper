package com.travelkeeper.service.google.firebase;

import java.io.Serializable;

/**
 * Service for interact with Google Firebase API
 *
 * Created by netocris on 21/09/2018
 */
public interface IFirebaseService extends Serializable {
    String getAll();
    void save(String value);
}
