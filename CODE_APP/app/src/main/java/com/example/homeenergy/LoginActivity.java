package com.example.homeenergy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText Email, Password;
    private Button Login;
    private LoginButton LoginByFB;
    private TextView passwordreset,Signup;
    private EditText passwordresetemail;
    private ProgressBar progressBar;
    private ProgressDialog processDialog;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.user);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.signIn);
        Signup = findViewById(R.id.textViewSignUp);
//        LoginByFB.setReadPermissions("email");

        passwordreset = findViewById(R.id.forgotPassword);
        passwordresetemail = findViewById(R.id.user);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        processDialog = new ProgressDialog(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager=CallbackManager.Factory.create();
        

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount(Email.getText().toString(), Password.getText().toString());
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resgisterAccount();
            }
        });
        passwordreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpasword();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookToken(AccessToken accessToken) {
        Log.d("FacebookAuthentication","handleFacebookToken" + accessToken);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("FacebookAuthentication", "sign in with credential: successful");
                    FirebaseUser user = mAuth.getCurrentUser();
                    toMain();
                } else {
                    Log.d("FacebookAuthentication","sign in with credential: failure");
                }
            }
        });
    }

    private void toMain() {
        Intent i =new Intent(this, MainActivity.class);
        startActivity(i);
    }
    private void resgisterAccount() {
        Intent intentres = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intentres);
    }
    public void loginUserAccount(String userEmail, String userPassword){
        if (TextUtils.isEmpty(userEmail)) {
            Email.setError("It's empty");
            Email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Password.setError("It's empty");
            Password.requestFocus();
            return;
        }
        processDialog.setMessage("Please Wait");
        processDialog.show();

        mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    processDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                    processDialog.dismiss();
                }
            }
        });
    }
    public void resetpasword(){
        final String resetemail = passwordresetemail.getText().toString();

        if (resetemail.isEmpty()) {
            passwordresetemail.setError("It's empty");
            passwordresetemail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(resetemail)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

}