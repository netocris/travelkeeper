package com.travelkeeper.service.google.drive;


import com.google.api.services.drive.model.File;

import java.io.Serializable;

/**
 * Service for interact with Google Drive API
 *
 * Created by netocris on 24/08/2018
 */
public interface IGoogleDriveService extends Serializable {

    /**
     *  Download a file from Google Drive
     *
     * @param file file to download
     *
     * @return the file
     */
    byte[] download(final String file);

    /**
     *  Upload a file into Google Drive cloud
     *
     * @param path path to file
     * @param file file to upload
     * @param mimeType file mimeType
     *
     * @return a file
     *
     */
    File upload(final String path, final String file, final String mimeType);

}
