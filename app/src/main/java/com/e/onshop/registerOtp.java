package com.e.onshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class registerOtp extends AppCompatActivity {
    Button mBtnVerifyOTP;
    EditText mOtp;
    String mname,add, mPhoneNumber;
    String OtpId , name;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_otp);
        add =  getIntent().getStringExtra("add");
        name = getIntent().getStringExtra("name");
        mPhoneNumber = getIntent().getStringExtra("number").toString();
        mBtnVerifyOTP = findViewById(R.id.verify);
        mOtp = findViewById(R.id.number);
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("fr");
         startPhoneNumberVerification(mPhoneNumber);

        mBtnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOtp.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter OTP", Toast.LENGTH_SHORT).show();
                }else if (mOtp.getText().toString().length()!=6){
                    Toast.makeText(getApplicationContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OtpId,mOtp.getText().toString());
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

                            FirebaseAuth auth= FirebaseAuth.getInstance();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Map<String , Object> info = new HashMap<>();
                            info.put("name" , name);
                            info.put("address " ,add);
                            info.put("phone_no", mPhoneNumber);
                            info.put("imgUrl", "https://firebasestorage.googleapis.com/v0/b/onshop-c3888.appspot.com/o/Image%2FlzRNapemIAbFZ5VsfxhozQQAh7n119011?alt=media&token=899110fd-cd45-4195-9821-cd446ba2f107");
                            db.collection("user").document(auth.getCurrentUser().getUid()).set(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(registerOtp.this, "added", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Toast.makeText(getApplicationContext(),auth.getUid(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(registerOtp.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(registerOtp.this, "Error...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
