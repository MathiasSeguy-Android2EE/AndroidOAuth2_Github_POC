package com.renaultnissan.acms.platform.oauth.githubsample.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.renaultnissan.acms.platform.oauth.githubsample.R;
import com.renaultnissan.acms.platform.oauth.githubsample.retrofit.OAuthServerIntf;
import com.renaultnissan.acms.platform.oauth.githubsample.retrofit.RetrofitBuilder;
import com.renaultnissan.acms.platform.oauth.githubsample.transverse.model.OAuthToken;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    /***********************************************************
    *  Attributes
    **********************************************************/
    /**
     * You client id, you have it from the google console when you register your project
     * https://console.developers.google.com/a
     */
    private static final String CLIENT_ID = "1020597890643-n3m1t7fplcv2t0f78g7miachq7lgnbrv.apps.googleusercontent.com";
    /**
     * The redirect uri you have define in your google console for your project
     */
    private static final String REDIRECT_URI = "com.renaultnissan.acms.platform.oauth.githubsample:/oauth2redirect";
    /**
     * The redirect root uri you have define in your google console for your project
     * It is also the scheme your Main Activity will react
     */
    private static final String REDIRECT_URI_ROOT = "com.renaultnissan.acms.platform.oauth.githubsample";
    /**
     * You are asking to use a code when autorizing
     */
    private static final String CODE = "code";
    /**
     * You are receiving an error when autorizing, it's embedded in this field
     */
    private static final String ERROR_CODE = "error";
    /**
     * GrantType:You are using a code when retrieveing the token
     */
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    /**
     * GrantType:You are using a refresh_token when retrieveing the token
     */
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    /**
     * The scope: what do we want to use
     * Here we want to be able to do anything on the user's GDrive
     */
    public static final String API_SCOPE = "https://www.googleapis.com/auth/drive";
    /**
     * The code returned by the server at the authorization's first step
     */
    private String code;
    /**
     * The error returned by the server at the authorization's first step
     */
    private String error;

    /***********************************************************
     * Managing Life Cycle
     **********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //You can be created either because your user start your application
        //        <intent-filter>
        //            <action android:name="android.intent.action.MAIN" />
        //            <category android:name="android.intent.category.LAUNCHER" />
        //        </intent-filter>
        //either because the callBack of the Authorisation is called :
        //        <intent-filter>
        //            <action android:name="android.intent.action.VIEW" />
        //            <category android:name="android.intent.category.BROWSABLE" />
        //            <category android:name="android.intent.category.DEFAULT" />
        //            <data android:scheme="com.renaultnissan.acms.platform.oauth.githubsample" />
        //        </intent-filter>

        //Manage the callback case:
        Uri data = getIntent().getData();
        if (data != null && !TextUtils.isEmpty(data.getScheme())) {
            if (REDIRECT_URI_ROOT.equals(data.getScheme())) {
                code = data.getQueryParameter(CODE);
                error=data.getQueryParameter(ERROR_CODE);
                Log.e(TAG, "onCreate: handle result of authorization with code :" + code);
                if (!TextUtils.isEmpty(code)) {
                    getTokenFormUrl();
                }
                if(!TextUtils.isEmpty(error)) {
                    //a problem occurs, the user reject our granting request or something like that
                    Toast.makeText(this, R.string.loginactivity_grantsfails_quit,Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onCreate: handle result of authorization with error :" + error);
                    //then die
                    finish();
                }
            }
        } else {
            //Manage the start application case:
            //If you don't have a token yet or if your token has expired , ask for it
            OAuthToken oauthToken=OAuthToken.Factory.create();
            if (oauthToken==null
                    ||oauthToken.getAccessToken()==null) {
                //first case==first token request
                if(oauthToken==null||oauthToken.getRefreshToken()==null){
                    Log.e(TAG, "onCreate: Launching authorization (first step)");
                    //first step of OAUth: the authorization step
                    makeAuthorizationRequest();
                }else{
                    Log.e(TAG, "onCreate: refreshing the token :" + oauthToken);
                    //refresh token case
                    refreshTokenFormUrl(oauthToken);
                }
            }
            //else just launch your MainActivity
            else {
                Log.e(TAG, "onCreate: Token available, just launch MainActivity");
                startMainActivity(false);
            }
        }
    }

    /***********************************************************
     *  Managing Authotization and Token process
     **********************************************************/

    /**
     * Make the Authorization request
     */
    private void makeAuthorizationRequest() {
        HttpUrl authorizeUrl = HttpUrl.parse("https://accounts.google.com/o/oauth2/v2/auth") //
                .newBuilder() //
                .addQueryParameter("client_id", CLIENT_ID)
                .addQueryParameter("scope", API_SCOPE)
                .addQueryParameter("redirect_uri", REDIRECT_URI)
                .addQueryParameter("response_type", CODE)
                .build();
        Intent i = new Intent(Intent.ACTION_VIEW);
        Log.e(TAG, "the url is : " + String.valueOf(authorizeUrl.url()));
        i.setData(Uri.parse(String.valueOf(authorizeUrl.url())));
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    /**
     * Refresh the OAuth token
     */
    private void refreshTokenFormUrl(OAuthToken oauthToken) {
        OAuthServerIntf oAuthServer = RetrofitBuilder.getSimpleClient(this);
        Call<OAuthToken> refreshTokenFormCall = oAuthServer.refreshTokenForm(
                oauthToken.getRefreshToken(),
                CLIENT_ID,
                GRANT_TYPE_REFRESH_TOKEN
        );
        refreshTokenFormCall.enqueue(new Callback<OAuthToken>() {
            @Override
            public void onResponse(Call<OAuthToken> call, Response<OAuthToken> response) {
                Log.e(TAG, "===============New Call==========================");
                Log.e(TAG, "The call refreshTokenFormUrl succeed with code=" + response.code() + " and has body = " + response.body());
                //ok we have the token
                response.body().save();
                startMainActivity(true);
            }

            @Override
            public void onFailure(Call<OAuthToken> call, Throwable t) {
                Log.e(TAG, "===============New Call==========================");
                Log.e(TAG, "The call refreshTokenFormCall failed", t);

            }
        });
    }

    /**
     * Retrieve the OAuth token
     */
    private void getTokenFormUrl() {
        OAuthServerIntf oAuthServer = RetrofitBuilder.getSimpleClient(this);
        Call<OAuthToken> getRequestTokenFormCall = oAuthServer.requestTokenForm(
                code,
                CLIENT_ID,
                REDIRECT_URI,
                GRANT_TYPE_AUTHORIZATION_CODE
        );
        getRequestTokenFormCall.enqueue(new Callback<OAuthToken>() {
            @Override
            public void onResponse(Call<OAuthToken> call, Response<OAuthToken> response) {
                Log.e(TAG, "===============New Call==========================");
                Log.e(TAG, "The call getRequestTokenFormCall succeed with code=" + response.code() + " and has body = " + response.body());
                //ok we have the token
                response.body().save();
                startMainActivity(true);
            }

            @Override
            public void onFailure(Call<OAuthToken> call, Throwable t) {
                Log.e(TAG, "===============New Call==========================");
                Log.e(TAG, "The call getRequestTokenFormCall failed", t);

            }
        });
    }

    /***********************************************************
     *  Others business methods
     **********************************************************/

    /**
     * Start the next activity
     */
    private void startMainActivity(boolean newtask) {
        Intent i = new Intent(this, MainActivity.class);
        if(newtask){
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(i);
        //you can die so
        finish();
    }

}
