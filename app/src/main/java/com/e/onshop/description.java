package com.e.onshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Freezable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.onshop.Fregments.HomeFragment;
import com.e.onshop.Fregments.cardFragment;
import com.e.onshop.MainActivity;
import com.e.onshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class description extends AppCompatActivity {

    ImageView image ,add , substrac;
    TextView name , bio ,count ,prise ;
    Button addProduct;
    Double totalprise;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String UserId,ProductName,ProductPrice,ProductImg,ProductBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        image = findViewById(R.id.productImage);
        add = findViewById(R.id.add);
        substrac = findViewById(R.id.substract);
        name = findViewById(R.id.name);
        bio = findViewById(R.id.info);
        count = findViewById(R.id.count);
        addProduct = findViewById(R.id.addProduct);
        prise = findViewById(R.id.prise);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();
        count.setText("1");

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ProgressDialog pd = new ProgressDialog(description.this);
                pd.setMessage("Product Adding");
                pd.show();
                HashMap<String,Object> map = new HashMap<>();
                map.put("TotalPrice",totalprise);
                map.put("UserId",UserId);
                map.put("ProductName",ProductName);
                map.put("ProductPrice",count.getText());
                map.put("ProductImg",ProductImg);
               firebaseFirestore.collection("Add-Card").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentReference> task) {
                       if (task.isComplete()){
                           pd.cancel();
                           Toast.makeText(description.this, "Add to card !!", Toast.LENGTH_SHORT).show();
                           description.super.onBackPressed();
                       }
                   }
               });


            }

        });

        FirebaseFirestore.getInstance().collection("Product").whereEqualTo("info",getIntent().getStringExtra("name")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()){
                      for (QueryDocumentSnapshot doc : task.getResult()){

                          ProductName = doc.get("ProductName").toString();
                          ProductBio = doc.get("info").toString();
                          ProductPrice =  doc.get("Prise").toString();
                          ProductImg =  doc.get("ProductUri").toString();
                          bio.setText(ProductBio);
                          name.setText(ProductName);
                          prise.setText("₹ "+ProductPrice);
                          Picasso.get().load(ProductImg).into(image);
                      }
                    }
                totalprise = Double.valueOf(ProductPrice);
            }
        });

      add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String n = count.getText().toString();
              final int total = Integer.parseInt( n)  +1 ;
             count.setText( Integer.toString(total) );
              FirebaseFirestore.getInstance().collection("Product").whereEqualTo("info",getIntent().getStringExtra("name")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      if(task.isComplete()){
                          for (QueryDocumentSnapshot doc : task.getResult()){
                              String prises = doc.get("Prise").toString();
                              totalprise = Double.valueOf((prises))*total;
                              prise.setText("₹ "+ String.valueOf(totalprise));
                          }
                      }
                  }
              });
          }
      });

        substrac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = count.getText().toString();
                final int total = Integer.parseInt( n)  -1 ;
                count.setText( Integer.toString(total) );
//             prise.setText(Integer.toString(priseTotal));
                FirebaseFirestore.getInstance().collection("Product").whereEqualTo("info",getIntent().getStringExtra("name")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                String prises = doc.get("Prise").toString();
                                Double totalprise =(Integer.valueOf(count.getText().toString())+1) *Double.valueOf(prises)  -Double.valueOf((prises));
                                prise.setText("₹ "+ String.valueOf(totalprise));
                            }
                        }
                    }
                });

            }
        });




    }
}