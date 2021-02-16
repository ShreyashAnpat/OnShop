package com.e.onshop.Fregments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.e.onshop.R;
import com.e.onshop.cardProductAdapter;
import com.e.onshop.payment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class cardFragment extends Fragment {

    RecyclerView recyclerView ;
    cardProductAdapter adapter ;
    FirebaseAuth auth = FirebaseAuth.getInstance() ;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    List<String> ProductNames , ProductImage ;
    String ProductPrise , TotalPrise ;
    List<String> ProductPrises;
    List<Double> TotalPrises ;
    String userid ;
    TextView total ;
    Double totals  = 0.00;
    Button cheakOut ;
    SwipeRefreshLayout refreshLayout ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_card, container, false);
        userid = auth.getUid() ;
        total = view.findViewById(R.id.total);
        cheakOut = view.findViewById(R.id.cheakOut);
        refreshLayout = view.findViewById(R.id.refresh);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                totals = 0.0 ;
                TotalPrises.clear();
                ProductPrises = new ArrayList<String>();
                ProductImage = new ArrayList<>();
                ProductNames = new ArrayList<>();
                TotalPrises = new ArrayList<>();
                firestore.collection("Add-Card").whereEqualTo("UserId", userid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()){
                            for (DocumentChange doc : task.getResult().getDocumentChanges()){
                                Log.d(TAG, "name : " + doc.getDocument().get("ProductName"));
                                ProductNames.add(doc.getDocument().get("ProductName").toString());
                                ProductImage.add(doc.getDocument().get("ProductImg").toString());
                                ProductPrises.add(doc.getDocument().get("ProductPrice").toString()) ;
                                TotalPrise = doc.getDocument().get("TotalPrice").toString() ;
                                TotalPrises.add(Double.valueOf(TotalPrise)) ;
                                totals = totals + Double.valueOf(TotalPrise);
                                total.setText("Total : " +totals.toString() +" ₹");
                            }
                        }
                        recyclerView = view.findViewById(R.id.cardProduct);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        if (ProductImage.size() <1){
                            total.setText("Total : 0.00 ₹");
                            cheakOut.setEnabled(false);
                            cheakOut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(view.getContext(), "No Product added ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                        adapter = new cardProductAdapter(view.getContext(), ProductNames,ProductImage,ProductPrises, TotalPrises,userid);
                        recyclerView.setAdapter(adapter);

                        refreshLayout.setRefreshing(false)  ;
                    }
                });



            }
        });

        cheakOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext() , payment.class));
            }
        });

        ProductPrises = new ArrayList<String>();
        ProductImage = new ArrayList<>();
        ProductNames = new ArrayList<>();
        TotalPrises = new ArrayList<>();

        firestore.collection("Add-Card").whereEqualTo("UserId", userid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()){
                    for (DocumentChange doc : task.getResult().getDocumentChanges()){
                        Log.d(TAG, "name : " + doc.getDocument().get("ProductName"));
                        ProductNames.add(doc.getDocument().get("ProductName").toString());
                        ProductImage.add(doc.getDocument().get("ProductImg").toString());
                        ProductPrises.add(doc.getDocument().get("ProductPrice").toString()) ;
                        TotalPrise = doc.getDocument().get("TotalPrice").toString() ;
                        TotalPrises.add(Double.valueOf(TotalPrise)) ;
                        totals = totals + Double.valueOf(TotalPrise);
                        total.setText("Total : " +totals.toString() +" ₹");
                    }
                }
                recyclerView = view.findViewById(R.id.cardProduct);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                adapter = new cardProductAdapter(view.getContext(), ProductNames,ProductImage,ProductPrises, TotalPrises,userid);
                recyclerView.setAdapter(adapter);

            }
        });

        return  view ;
    }
}