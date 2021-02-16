package com.e.onshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class start extends AppCompatActivity {
    ImageView logo ;
    TextView text ;
    private Object Intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo);
        text = findViewById(R.id.text);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(start.this, wellcome.class));
                finish();
            }
        },3000);
    }


}