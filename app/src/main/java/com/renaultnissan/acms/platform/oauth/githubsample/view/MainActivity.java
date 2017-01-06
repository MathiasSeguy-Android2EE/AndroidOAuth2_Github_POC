package com.renaultnissan.acms.platform.oauth.githubsample.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.renaultnissan.acms.platform.oauth.githubsample.R;
import com.renaultnissan.acms.platform.oauth.githubsample.retrofit.OAuthServerIntf;
import com.renaultnissan.acms.platform.oauth.githubsample.retrofit.RetrofitBuilder;
import com.renaultnissan.acms.platform.oauth.githubsample.transverse.model.GDriveFiles;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    /***********************************************************
    *  Attributes
    **********************************************************/
    Button btnDo;
    TextView txvResult;
    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDo= (Button) findViewById(R.id.btnDo);
        btnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listGDriveUserFiles();
            }
        });
        txvResult= (TextView) findViewById(R.id.txvResult);
        txvResult.setMovementMethod(new ScrollingMovementMethod());

    }

    /***********************************************************
    *  Business Methods
    **********************************************************/
    /**
     * Make an Https call using the Authentication token
     * Here we list the files on GDrive
     */
    private void listGDriveUserFiles(){
        OAuthServerIntf server=RetrofitBuilder.getOAuthClient(this);
        Call<GDriveFiles> listFilesCall=server.listFiles();
        listFilesCall.enqueue(new Callback<GDriveFiles>() {
            @Override
            public void onResponse(Call<GDriveFiles> call, Response<GDriveFiles> response) {
                Log.e(TAG,"The call listFilesCall succeed with [code="+response.code()+" and has body = "+response.body()+" and message = "+response.message()+" ]");
                //ok we have the list of files on GDrive
                if(response.code()==200&&response.body()!=null){
                    txvResult.setText(response.body().toString());
                }else if(response.code()==400){
                    txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_400));
                }else if(response.code()==401){
                    txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_401));
                }else if(response.code()==403){
                    txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_403));
                }else if(response.code()==404){
                    txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_404));
                }
            }

            @Override
            public void onFailure(Call<GDriveFiles> call, Throwable t) {
                Log.e(TAG,"The call listFilesCall failed",t);
            }
        });
    }
}
