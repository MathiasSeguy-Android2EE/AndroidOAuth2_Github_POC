/**
 * <ul>
 * <li>RetrofitBuilder</li>
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

package com.renaultnissan.acms.platform.oauth.githubsample.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.renaultnissan.acms.platform.oauth.githubsample.retrofit.converters.StringConverterFactory;
import com.renaultnissan.acms.platform.oauth.githubsample.retrofit.interceptors.OAuthInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Mathias Seguy - Android2EE on 05/01/2017.
 */
public class RetrofitBuilder {
    /***********************************************************
    *  Constants
    **********************************************************/
    /**
     * Root URL
     * (always ends with a /)
     */
    public static final String BASE_URL = "https://www.googleapis.com/";

    /***********************************************************
     * Getting OAuthServerIntf instance using Retrofit creation
     **********************************************************/
    /**
     * A basic client to make unauthenticated calls
     * @param ctx
     * @return OAuthServerIntf instance
     */
    public static OAuthServerIntf getSimpleClient(Context ctx) {
        //Using Default HttpClient
        Retrofit retrofit = new Retrofit.Builder()
                .client(getSimpleOkHttpClient(ctx))
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        OAuthServerIntf webServer = retrofit.create(OAuthServerIntf.class);
        return webServer;
    }

    /**
     * An autenticated client to make authenticated calls
     * The token is automaticly added in the Header of the request
     * @param ctx
     * @return OAuthServerIntf instance
     */
    public static OAuthServerIntf getOAuthClient(Context ctx) {
        // now it's using the cach
        // Using my HttpClient
        Retrofit raCustom = new Retrofit.Builder()
                .client(getOAuthOkHttpClient(ctx))
                .baseUrl(BASE_URL)
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        OAuthServerIntf webServer = raCustom.create(OAuthServerIntf.class);
        return webServer;
    }

    /***********************************************************
     * OkHttp Clients
     **********************************************************/

    /**
     * Return a simple OkHttpClient v:
     * have a cache
     * have a HttpLogger
     */
    @NonNull
    public static OkHttpClient getSimpleOkHttpClient(Context ctx) {
        // Define the OkHttp Client with its cache!
        // Assigning a CacheDirectory
        File myCacheDir=new File(ctx.getCacheDir(),"OkHttpCache");
        // You should create it...
        int cacheSize=1024*1024;
        Cache cacheDir=new Cache(myCacheDir,cacheSize);
        HttpLoggingInterceptor httpLogInterceptor=new HttpLoggingInterceptor();
        httpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                //add a cache
                .cache(cacheDir)
                .addInterceptor(httpLogInterceptor)
                .build();
    }

    /**
     * Return a OAuth OkHttpClient v:
     * have a cache
     * have a HttpLogger
     * add automaticly the token in the header of each request because of the oAuthInterceptor
     * @param ctx
     * @return
     */
    @NonNull
    public static OkHttpClient getOAuthOkHttpClient(Context ctx) {
        // Define the OkHttp Client with its cache!
        // Assigning a CacheDirectory
        File myCacheDir=new File(ctx.getCacheDir(),"OkHttpCache");
        // You should create it...
        int cacheSize=1024*1024;
        Cache cacheDir=new Cache(myCacheDir,cacheSize);
        Interceptor oAuthInterceptor=new OAuthInterceptor();
        HttpLoggingInterceptor httpLogInterceptor=new HttpLoggingInterceptor();
        httpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .cache(cacheDir)
                .addInterceptor(oAuthInterceptor)
                .addInterceptor(httpLogInterceptor)
                .build();
    }
}
