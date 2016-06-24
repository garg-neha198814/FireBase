package com.mobilyte.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEmail,mPwd;
    private Button mCreateUser;
    private Firebase rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
    }
    private void initViews() {
        mEmail = (EditText) findViewById(R.id.email);
        mPwd = (EditText) findViewById(R.id.pwd);
        mCreateUser = (Button) findViewById(R.id.create_user);
        rootRef = new Firebase("https://glaring-torch-854.firebaseio.com/");
        mCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String pwd = mPwd.getText().toString();
                if(email.isEmpty()||pwd.isEmpty()){
                    Toast.makeText(getApplicationContext(),"enter all fields",Toast.LENGTH_SHORT).show();
                }else{
                    rootRef.createUser(email, pwd, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            System.out.println("Successfully created user account with uid: " + result.get("uid"));
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
                        }
                        @Override
                        public void onError(FirebaseError firebaseError) {
                            System.out.println("firebaseerror>>>>"+firebaseError.getMessage());
                            // there was an error
                        }
                    });
                }
            }
        });

    }
}
