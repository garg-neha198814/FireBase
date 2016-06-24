package com.mobilyte.firebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText mEmail,mPwd;
    private Button mCreateUser,mTwitterLogin;
    private Firebase rootRef;
    public static String sharedFile="fireBase";
    private SharedPreferences pref;
    Map<String, String> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rootRef = new Firebase("https://glaring-torch-854.firebaseio.com/");
        initViews();
    }

    private void initViews() {
        mEmail = (EditText) findViewById(R.id.login_email);
        mPwd = (EditText) findViewById(R.id.login_pwd);
        mCreateUser = (Button) findViewById(R.id.login_user);
        mTwitterLogin = (Button) findViewById(R.id.twitter_login);
        options = new HashMap<String, String>();
        options.put("oauth_token", "<OAuth token>");
        options.put("oauth_token_secret", "<OAuth token secret>");
        options.put("user_id", "<Twitter user id>");
        mTwitterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootRef.authWithOAuthToken("twitter", options, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        System.out.println("twitter authenicate");
                        // the Twitter user is now authenticated with your Firebase app
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // there was an error
                        System.out.println("firebase error>>>"+firebaseError);
                    }
                });
            }
        });
        mCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String pwd = mPwd.getText().toString();
                if(email.isEmpty()||pwd.isEmpty()){
                    Toast.makeText(getApplicationContext(),"enter all fields",Toast.LENGTH_SHORT).show();
                }else{
                    rootRef.authWithPassword(email,pwd, new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                            pref = getSharedPreferences(sharedFile,0);
                           // pref.edit().
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            System.out.println("login error >>>"+firebaseError.getMessage());
                            // there was an error
                        }
                    });
                }
            }
        });

    }
}
