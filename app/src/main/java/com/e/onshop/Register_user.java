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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class Register_user extends AppCompatActivity {

    EditText name ,village , taluka,dist, near_by ;
    CountryCodePicker ccp ;
    EditText phonenumber ;
    Button getotp ;
    TextView login ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        name = findViewById(R.id.name);
        ccp = findViewById(R.id.ccp);
        phonenumber = findViewById(R.id.phone_number);
        ccp.registerCarrierNumberEditText(phonenumber);
        getotp = findViewById(R.id.verify);
        village = findViewById(R.id.velage);
        taluka = findViewById(R.id.taluka);
        dist = findViewById(R.id.district);
        near_by = findViewById(R.id.nearBy);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register_user.this , login.class));
                finish();
            }
        });

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_user.this , registerOtp.class);
                intent.putExtra("name" , name.getText().toString());
                intent.putExtra("add", village.getText().toString()+" "+taluka.getText().toString()+" "+dist.getText().toString()+" "+near_by.getText().toString());
                intent.putExtra("number",ccp.getFullNumberWithPlus().replace(" ",""));

                startActivity(intent);
            }
        });

    }
}