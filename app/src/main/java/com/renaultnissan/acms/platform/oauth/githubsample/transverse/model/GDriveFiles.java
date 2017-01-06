/**
 * <ul>
 * <li>GDriveFiles</li>
 * <li>com.renaultnissan.acms.platform.oauth.githubsample.transverse.model</li>
 * <li>06/01/2017</li>
 * <p>
 * <li>======================================================</li>
 * <p>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.renaultnissan.acms.platform.oauth.githubsample.transverse.model;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 06/01/2017.
 */
public class GDriveFiles {
    String kind;
    String nextPageToken;
    List<GDriveFile> files;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GDriveFiles{");
        sb.append(",\r\n kind='").append(kind).append('\'');
        sb.append(",\r\n nextPageToken='").append(nextPageToken).append('\'');
        sb.append("\r\nfiles=\r\n");
        for (GDriveFile file : files) {
            sb.append(file.toString("\t"));
        }
        sb.append('}');
        return sb.toString();
    }
}
