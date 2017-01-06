/**
 * <ul>
 * <li>OAuthToken</li>
 * <li>com.renaultnissan.acms.platform.oauth.githubsample.retrofit</li>
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

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.renaultnissan.acms.platform.oauth.githubsample.MyApplication;
import com.squareup.moshi.Json;

/**
 * Created by Mathias Seguy - Android2EE on 06/01/2017.
 */
public class OAuthToken {
    private static final String TAG = "OAuthToken";
    /***********************************************************
     * Constants
     **********************************************************/
    private static final String OAUTH_SHARED_PREFERENCE_NAME = "OAuthPrefs";
    private static final String SP_TOKEN_KEY = "token";
    private static final String SP_TOKEN_TYPE_KEY = "token_type";
    private static final String SP_TOKEN_EXPIRED_AFTER_KEY = "expired_after";
    private static final String SP_REFRESH_TOKEN_KEY = "refresh_token";

    /***********************************************************
     * Attributes
     **********************************************************/
    @Json(name = "access_token")
    private String accessToken;
    @Json(name = "token_type")
    private String tokenType;
    @Json(name = "expires_in")
    private long expiresIn;
    private long expiredAfterMilli = 0;
    @Json(name = "refresh_token")
    private String refreshToken;

    /***********************************************************
     * Managing Persistence
     **********************************************************/
    public void save() {
        Log.e(TAG, "Savng the following element " + this);
        //update expired_after
        expiredAfterMilli = System.currentTimeMillis() + expiresIn * 1000;
        Log.e(TAG, "Savng the following element and expiredAfterMilli =" + expiredAfterMilli+" where now="+System.currentTimeMillis()+" and expired in ="+ expiresIn);
        SharedPreferences sp = MyApplication.instance.getSharedPreferences(OAUTH_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(SP_TOKEN_KEY, accessToken);
        ed.putString(SP_TOKEN_TYPE_KEY, tokenType);
        ed.putLong(SP_TOKEN_EXPIRED_AFTER_KEY, expiredAfterMilli);
        ed.putString(SP_REFRESH_TOKEN_KEY, refreshToken);
        ed.commit();
    }

    /***********************************************************
     * Getters and Setters
     **********************************************************/
    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    void setExpiredAfterMilli(long expiredAfterMilli) {
        this.expiredAfterMilli = expiredAfterMilli;
    }

    void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OAuthToken{");
        sb.append("accessToken='").append(accessToken).append('\'');
        sb.append(", tokenType='").append(tokenType).append('\'');
        sb.append(", expires_in=").append(expiresIn);
        sb.append(", expiredAfterMilli=").append(expiredAfterMilli);
        sb.append(", refreshToken='").append(refreshToken).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /***********************************************************
     * Factory Pattern
     **********************************************************/

    public static class Factory {
        private static final String TAG = "OAuthToken.Factory";

        public static OAuthToken create() {
            long expiredAfter = 0;
            SharedPreferences sp = MyApplication.instance.getSharedPreferences(OAUTH_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
            if (sp.contains(SP_TOKEN_EXPIRED_AFTER_KEY)) {
                Log.e(TAG, "sp.contains(SP_TOKEN_EXPIRED_AFTER)");
                expiredAfter = sp.getLong(SP_TOKEN_EXPIRED_AFTER_KEY, 0);
                long now = System.currentTimeMillis();

                Log.e(TAG, "Delta : " + (now - expiredAfter));
                if (expiredAfter == 0 || now > expiredAfter) {
                    Log.e(TAG, "expiredAfter==0||now>expiredAfter, token has expired");
                    //flush token in the SP
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString(SP_TOKEN_KEY, null);
                    ed.commit();
                    //rebuild the object according to the SP
                    OAuthToken oauthToken = new OAuthToken();
                    oauthToken.setAccessToken(null);
                    oauthToken.setTokenType(sp.getString(SP_TOKEN_TYPE_KEY, null));
                    oauthToken.setRefreshToken(sp.getString(SP_REFRESH_TOKEN_KEY, null));
                    oauthToken.setExpiredAfterMilli(sp.getLong(SP_TOKEN_EXPIRED_AFTER_KEY, 0));
                    return oauthToken;
                } else {

                    Log.e(TAG, "NOT (expiredAfter==0||now<expiredAfter) current case, token is valid");
                    //rebuild the object according to the SP
                    OAuthToken oauthToken = new OAuthToken();
                    oauthToken.setAccessToken(sp.getString(SP_TOKEN_KEY, null));
                    oauthToken.setTokenType(sp.getString(SP_TOKEN_TYPE_KEY, null));
                    oauthToken.setRefreshToken(sp.getString(SP_REFRESH_TOKEN_KEY, null));
                    oauthToken.setExpiredAfterMilli(sp.getLong(SP_TOKEN_EXPIRED_AFTER_KEY, 0));
                    return oauthToken;
                }
            } else {
                return null;
            }
        }
    }

}
