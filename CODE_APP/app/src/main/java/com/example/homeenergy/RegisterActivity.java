package com.example.homeenergy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText textUsername, textEmail,textPassword, textConfirmPass;
    public Button registerBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textUsername =findViewById(R.id.inputUser);
        textPassword =findViewById(R.id.inputPassword);
        textEmail = findViewById(R.id.emailAddress);
        textConfirmPass = findViewById(R.id.confirmPassword);
        registerBtn = findViewById(R.id.buttonRegister);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void registerUser() {
        final String name = textUsername.getText().toString().trim();
        final String email = textEmail.getText().toString();

        String password = textPassword.getText().toString().trim();
        String cpassword = textConfirmPass.getText().toString().trim();
        if (email.isEmpty()) {
            textEmail.setError("It's empty");
            textEmail.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            textUsername.setError("It's Empty");
            textUsername.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textEmail.setError("Not a valid emailaddress");
            textEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            textPassword.setError("Its empty");
            textPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            textPassword.setError("Less length");
            textPassword.requestFocus();
            return;
        }
        if(!password.equals(cpassword)){
            textConfirmPass.setError("Password Do not Match");
            textConfirmPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(i);
                            Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_LONG).show();

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}