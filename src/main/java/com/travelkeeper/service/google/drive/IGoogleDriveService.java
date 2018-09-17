package com.travelkeeper.service.google.drive;


import com.google.api.services.drive.model.File;

import java.io.Serializable;

/**
 * Service for interact with Google Drive API
 *
 * Created by netocris on 24/08/2018
 */
public interface IGoogleDriveService extends Serializable {

//    /**
//     *  Download a file from Google Drive
//     *
//     * @param File file name
//     *
//     * @return the file
//     */
//    File download(final String File);

    /**
     *  Upload a file into Google Drive cloud
     *
     * @param path path to file
     * @param name file name
     * @param mimeType file mimeType
     *
     * @return a file
     *
     */
    File upload(final String path, final String name, final String mimeType);

}
