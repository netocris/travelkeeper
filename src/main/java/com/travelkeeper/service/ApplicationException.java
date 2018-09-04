package com.travelkeeper.service;

/**
 * Base application error handler
 *
 * Created by netocris on 24/08/2018
 */
class ApplicationException extends RuntimeException {

    ApplicationException(String message) {
        super(message);
    }

}
