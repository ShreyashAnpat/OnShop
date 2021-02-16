package com.e.onshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class get_OTP extends AppCompatActivity {
    Button mBtnVerifyOTP;
    EditText mOtp1, mOtp2, mOtp3,mOtp4 , mOtp5,mOtp6 ;
    String mOtp;
    String mPhoneNumber;
    String OtpId , name;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get__o_t_p);

//        name = getIntent().getStringExtra("names").toString();
        mPhoneNumber = getIntent().getStringExtra("number").toString();
        mBtnVerifyOTP = findViewById(R.id.verify);
        mOtp1 = findViewById(R.id.number1);
        mOtp2 = findViewById(R.id.number2);
        mOtp3 = findViewById(R.id.number3);
        mOtp4 = findViewById(R.id.number4);
        mOtp5 = findViewById(R.id.number5);
        mOtp6 = findViewById(R.id.number6);


        mAuth = FirebaseAuth.getInstance();





        mAuth.setLanguageCode("fr");


        Toast.makeText(this, mPhoneNumber, Toast.LENGTH_SHORT).show();

        startPhoneNumberVerification(mPhoneNumber);

        mBtnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amOtp = mOtp1.getText().toString() + mOtp2.getText().toString() + mOtp3.getText().toString() + mOtp4.getText().toString() + mOtp5.getText().toString() + mOtp6.getText().toString();



                if (amOtp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter OTP", Toast.LENGTH_SHORT).show();
                }else if (amOtp.length()!=6){
                    Toast.makeText(getApplicationContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OtpId,amOtp);
                        signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }
    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                OtpId = verificationId ;


            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(get_OTP.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                             Toast.makeText(get_OTP.this    , "Error...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}