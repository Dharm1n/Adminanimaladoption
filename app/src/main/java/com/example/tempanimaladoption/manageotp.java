package com.example.tempanimaladoption;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class manageotp<mCallbacks> extends AppCompatActivity {
    EditText otp;
    Button verify;
    String phoneno;
    String sentotp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageotp);
        phoneno=getIntent().getStringExtra("mobile").toString();
        otp=(EditText)findViewById(R.id.otp);
        verify=(Button)findViewById(R.id.verify);
        mAuth=FirebaseAuth.getInstance();
        initiateotp();

        verify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(otp.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please enter the OTP...",Toast.LENGTH_LONG).show();
                }
                else if(otp.getText().toString().length()!=6) {
                    Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_LONG).show();
                }
                else
                {
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(sentotp,otp.getText().toString());
                    verifyotp(credential);
                }
            }
        });

    }

    private void initiateotp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                          @Override
                                          public void onVerificationCompleted(PhoneAuthCredential credential) {
                                              verifyotp(credential);
                                          }

                                          @Override
                                          public void onVerificationFailed(@NonNull FirebaseException e) {
                                              Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                          }

                                          @Override
                                          public void onCodeSent(@NonNull String verificationId,
                                                                 @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                              sentotp=verificationId;
                                          }
                                      }
                        )
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void verifyotp(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent=new Intent(manageotp.this, admin.class);
                            intent.putExtra("mobile",phoneno);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),"ERROR please try again later...",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}