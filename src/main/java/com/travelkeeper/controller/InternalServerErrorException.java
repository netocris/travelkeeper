package com.travelkeeper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Internal Server Error errors
 *
 * Created by netocris on 04/09/2018
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerErrorException extends RuntimeException {

    InternalServerErrorException(Throwable cause) {
        super(cause);
    }

}
