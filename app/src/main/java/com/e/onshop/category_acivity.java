package com.e.onshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class category_acivity extends AppCompatActivity {

    TextView header_name ;
    RecyclerView categoryItem ;
    categoryAdapter adapter ;
    ArrayList<String> productsName ;
    ArrayList<String> ProductImage , productWeight;
    ArrayList<String> productPrise ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_acivity);
        categoryItem = findViewById(R.id.CategoryItem);

          productsName =new ArrayList<>();
          ProductImage = new ArrayList<>();
          productPrise = new ArrayList<>();
          productWeight = new ArrayList<>();
        final ArrayList<Object> productInfo = new ArrayList<>();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        FirebaseFirestore.getInstance().collection("Product").whereEqualTo("Category",getIntent().getStringExtra("category")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()){
                    for(QueryDocumentSnapshot doc :task.getResult() ){

                        ProductImage.add(doc.get("ProductUri").toString());
                        productsName.add(doc.get("ProductName").toString());
                        productPrise.add(doc.get("Prise").toString());
                        productWeight.add(doc.get("Weight").toString());
                        productInfo.add(doc.get("info").toString());
                    }

                }

                progressDialog.cancel();
                categoryItem.setLayoutManager(new LinearLayoutManager(category_acivity.this));
                adapter =new categoryAdapter(category_acivity.this, ProductImage,productsName, productPrise,productWeight ,productInfo);
                categoryItem.setAdapter(adapter);
            }
        });
//

    }
}