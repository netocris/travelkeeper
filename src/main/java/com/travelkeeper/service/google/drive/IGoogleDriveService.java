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
     * @param filename file to download
     *
     * @return the file
     */
    byte[] download(final String filename);

    /**
     *  Upload a file into Google Drive cloud
     *
     * @param path path to file
     * @param mimeType file mimeType
     * @param filename file to upload
     * @param content file content
     *
     * @return a file
     *
     */
    File upload(final String path, final String mimeType, final String filename, final byte[] content);

}
