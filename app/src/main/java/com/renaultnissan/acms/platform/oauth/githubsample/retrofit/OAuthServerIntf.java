/**
 * <ul>
 * <li>OAuthServerIntf</li>
 * <li>com.renaultnissan.acms.platform.oauth.githubsample.retrofit</li>
 * <li>04/01/2017</li>
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
package com.renaultnissan.acms.platform.oauth.githubsample.retrofit;

import com.renaultnissan.acms.platform.oauth.githubsample.transverse.model.GDriveFiles;
import com.renaultnissan.acms.platform.oauth.githubsample.transverse.model.OAuthToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Mathias Seguy - Android2EE on 04/01/2017.
 */
public interface OAuthServerIntf {
    /**
     * The call to request a token
     */
    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Call<OAuthToken> requestTokenForm(
            @Field("code")String code,
            @Field("client_id")String client_id,
//            @Field("client_secret")String client_secret, //Is not relevant for Android application
            @Field("redirect_uri")String redirect_uri,
            @Field("grant_type")String grant_type);

    /**
     * The call to refresh a token
     */
    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Call<OAuthToken> refreshTokenForm(
            @Field("refresh_token")String refresh_token,
            @Field("client_id")String client_id,
//            @Field("client_secret")String client_secret, //Is not relevant for Android application
            @Field("grant_type")String grant_type);
    /**
     * The call to retrieve the files of our User in GDrive
     */
    @GET("drive/v3/files")
    Call<GDriveFiles> listFiles();

}
