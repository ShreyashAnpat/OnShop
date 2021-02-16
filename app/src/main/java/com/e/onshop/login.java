package com.e.onshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;

public class login extends AppCompatActivity {
    CountryCodePicker ccp ;
    EditText phonenumber ;
    Button getotp ;
    TextView sign_up ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sign_up = findViewById(R.id.sign_up);
        ccp = findViewById(R.id.ccp);
        phonenumber = findViewById(R.id.number);
        ccp.registerCarrierNumberEditText(phonenumber);
        getotp = findViewById(R.id.getOtp);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this , Register_user.class));
                finish();
            }
        });
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this , get_OTP.class);
                intent.putExtra("number",ccp.getFullNumberWithPlus().replace(" ",""));

                startActivity(intent);
                finish();
            }
        });


    }


}