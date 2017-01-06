/**
 * <ul>
 * <li>OAuthInterceptor</li>
 * <li>com.renaultnissan.acms.platform.oauth.githubsample.retrofit</li>
 * <li>05/01/2017</li>
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

package com.renaultnissan.acms.platform.oauth.githubsample.retrofit.interceptors;

import android.text.TextUtils;
import android.util.Log;

import com.renaultnissan.acms.platform.oauth.githubsample.transverse.model.OAuthToken;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mathias Seguy - Android2EE on 05/01/2017.
 * This class aims to add automaticly in the Header the OAuth token
 */
public class OAuthInterceptor implements Interceptor {
    private static final String TAG = "OAuthInterceptor";
    private String accessToken,accessTokenType;
    @Override
    public Response intercept(Chain chain) throws IOException {
        //find the token
        OAuthToken oauthToken=OAuthToken.Factory.create();
        accessToken=oauthToken.getAccessToken();
        accessTokenType=oauthToken.getTokenType();
        //add it to the request
        Request.Builder builder = chain.request().newBuilder();
        if (!TextUtils.isEmpty(accessToken)&&!TextUtils.isEmpty(accessTokenType)) {
            Log.e(TAG,"In the interceptor adding the header authorization with : "+accessTokenType+" " + accessToken);
            builder.header("Authorization",accessTokenType+" " + accessToken);
        }else{
            Log.e(TAG,"In the interceptor there is a fuck with : "+accessTokenType+" " + accessToken);
        }
        //proceed to the call
        return chain.proceed(builder.build());
    }
}
