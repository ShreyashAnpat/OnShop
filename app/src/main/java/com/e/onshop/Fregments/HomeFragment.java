package com.e.onshop.Fregments;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.e.onshop.R;
import com.e.onshop.laptopAdapter;
import com.e.onshop.headPhoneAdapter;
import com.e.onshop.otheraccessoriesAdapter;
import com.e.onshop.phoneAdapter;
import com.e.onshop.productAdapter;
import com.e.onshop.searchAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class    HomeFragment extends Fragment {
    ViewFlipper viewFlipper;
    RecyclerView recyclerView  , laptop , phone,otherAccessories,OtherAccessories;
    productAdapter adapter ;
    phoneAdapter phoneAdapter ;
    laptopAdapter  laptopAdapter ;
    headPhoneAdapter headPhoneAdapter;
    otheraccessoriesAdapter otheraccessoriesAdapter ;
    ArrayList<String> Category_names , Laptop_name,info , laptopPrise,laptopImage ,phoneImage, phoneInfo , phoneName,phonePrise ,headphoneImage,
                        headphoneName,headphoneInfo,headphonePrise ,otheraccessoriesName ,otheraccessoriesInfo ,otheraccessoriesImage ,
                        otheraccessoriesPrise , ProductImage , ProductName ,ProductPrise  , productInfo ;
    ArrayList<Integer> imagelist  ;
    SearchView searchbar ;
    String name  ;
    searchAdapter searchAdapter;
    RecyclerView search ;
    ScrollView scrollView ;
    SwipeRefreshLayout refresh ;
    DrawerLayout drawerLayout ;
    TextView text ;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        laptop = view.findViewById(R.id.laptop);
        phone = view.findViewById(R.id.Phones);
        otherAccessories = view.findViewById(R.id.other);
        OtherAccessories = view.findViewById(R.id.otherAccessories);
        searchbar = view.findViewById(R.id.searchbar);
        search = view.findViewById(R.id.searchproduct);
        scrollView = view.findViewById(R.id.scrollView);
        refresh = view.findViewById(R.id.refresh);


        text  = view.findViewById(R.id.text);


        String[] arays = new String[]{"Vegetable","accessories" , "Dry food", "Grocery" ,"Food","Laptops"};

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                Laptop_name = new ArrayList<>();
                laptopImage = new ArrayList<>();
                laptopPrise = new ArrayList<>();
                info = new ArrayList<>();
                FirebaseFirestore.getInstance().collection("Product").whereEqualTo("Category","Laptops").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete()){
                            for(QueryDocumentSnapshot doc :task.getResult() ){
                                info.add(doc.get("info").toString());
                                Laptop_name.add(doc.get("ProductName").toString());
                                laptopImage.add(doc.get("ProductUri").toString());
                                laptopPrise.add("₹ "+ doc.get("Prise").toString());

                            }
                        }

                        laptop.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,false));
                        laptopAdapter = new laptopAdapter(view.getContext(),Laptop_name,info,laptopImage,laptopPrise);
                        laptop.setAdapter(laptopAdapter);



                    }
                });


                phoneImage = new ArrayList<>();
                phoneName = new ArrayList<>();
                phoneInfo = new ArrayList<>();
                phonePrise = new ArrayList<>();
                FirebaseFirestore.getInstance().collection("Product").whereEqualTo("Category","Phones").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                phoneName.add(doc.get("ProductName").toString()) ;
                                phoneInfo.add(doc.get("info").toString());
                                phoneImage.add((doc.get("ProductUri").toString()));
                                phonePrise.add(doc.get("Prise").toString());

                            }
                        }


                        phone.setLayoutManager(new LinearLayoutManager(view.getContext() , LinearLayoutManager.HORIZONTAL , false));
                        phoneAdapter = new phoneAdapter(view.getContext(),phoneName,phonePrise,phoneImage,phoneInfo);
                        phone.setAdapter(phoneAdapter);
                    }
                });
                headphoneImage = new ArrayList<>();
                headphoneName = new ArrayList<>();
                headphoneInfo = new ArrayList<>();
                headphonePrise = new ArrayList<>();
                FirebaseFirestore.getInstance().collection("Product").whereEqualTo("Category","headphone").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                headphoneName.add(doc.get("ProductName").toString()) ;
                                headphoneInfo.add(doc.get("info").toString());
                                headphoneImage.add((doc.get("ProductUri").toString()));
                                headphonePrise.add(doc.get("Prise").toString());
                            }
                        }
//                Toast.makeText(getContext(), otherName.toString(), Toast.LENGTH_SHORT).show();

                        otherAccessories.setLayoutManager(new LinearLayoutManager(view.getContext() , LinearLayoutManager.HORIZONTAL , false));
                        headPhoneAdapter = new headPhoneAdapter(view.getContext(),headphoneName,headphonePrise,headphoneImage,headphoneInfo);
                        otherAccessories.setAdapter(headPhoneAdapter);
                    }
                });

                otheraccessoriesName = new ArrayList<>();
                otheraccessoriesInfo = new ArrayList<>();
                otheraccessoriesImage = new ArrayList<>();
                otheraccessoriesPrise = new ArrayList<>();
                FirebaseFirestore.getInstance().collection("Product").whereEqualTo("Category","otherAccessories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                otheraccessoriesName.add(doc.get("ProductName").toString()) ;
                                otheraccessoriesInfo.add(doc.get("info").toString());
                                otheraccessoriesImage.add((doc.get("ProductUri").toString()));
                                otheraccessoriesPrise.add(doc.get("Prise").toString());
                            }
                        }
//                Toast.makeText(getContext(), otheraccessoriesName.toString(), Toast.LENGTH_SHORT).show();

                        OtherAccessories.setLayoutManager(new LinearLayoutManager(view.getContext() , LinearLayoutManager.HORIZONTAL , false));
                        otheraccessoriesAdapter= new otheraccessoriesAdapter(view.getContext(),otheraccessoriesName,otheraccessoriesPrise,otheraccessoriesImage,otheraccessoriesInfo);
                        OtherAccessories.setAdapter(otheraccessoriesAdapter);
                    }
                });
                refresh.setRefreshing(false);

            }
        });

        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ProductImage = new ArrayList<>();
                ProductName = new ArrayList<>();
                ProductPrise = new ArrayList<>();
                productInfo = new ArrayList<>();

                if (!newText.isEmpty()){
                    search.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    Query query = FirebaseFirestore.getInstance().collection("Product").orderBy("info").startAt(newText ).endAt(newText+"\uf9ff" );
                    query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                ProductImage.clear();
                                ProductPrise.clear();
                                ProductName.clear();
                                productInfo.clear();
                            for (DocumentChange doc : value.getDocumentChanges()){
                                if (doc.getType()== DocumentChange.Type.ADDED){
                                    ProductImage.add(doc.getDocument().get("ProductUri").toString());
                                    ProductName.add(doc.getDocument().get("ProductName").toString());
                                    ProductPrise.add(doc.getDocument().get("Prise").toString());
                                    productInfo.add(doc.getDocument().get("info").toString());

                                }else {
                                    ProductPrise.clear();
                                    ProductPrise.clear();
                                    ProductImage.clear();
                                    productInfo.clear();
                                }

                                search.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                searchAdapter = new searchAdapter(view.getContext(),ProductName,ProductPrise,ProductImage , productInfo);
                                search.setAdapter(searchAdapter);
                            }
                        }
                    });
                }else {
                    ProductPrise.clear();
                    ProductPrise.clear();
                    ProductImage.clear();
                    productInfo.clear();
                    search.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                }

                return false;
            }
        });



        int images[] = { R.drawable.eat_vegetables,R.drawable.accesseris,R.drawable.dryfoods,R.drawable.grocery,R.drawable.food};
        Category_names = new ArrayList<>();
        for (String name:arays){
            Category_names.add(name);
        }
        imagelist = new ArrayList<Integer>();
        for (int image : images){
            imagelist.add( image );
        }

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Adding Products");
        progressDialog.show();

//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);

        viewFlipper = view.findViewById(R.id.slide);

        int slides[] = {R.drawable.add , R.drawable.add1 };
        for(int image :slides){
            ImageView imageView = new ImageView(view.getContext());
            imageView.setBackgroundResource(image);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(4000);
            viewFlipper.setAutoStart(true);
            viewFlipper.setInAnimation(view.getContext(), android.R.anim.slide_in_left );
            viewFlipper.setOutAnimation(view.getContext(), android.R.anim.slide_out_right);


        }




        Laptop_name = new ArrayList<>();
        laptopImage = new ArrayList<>();
        laptopPrise = new ArrayList<>();
        info = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Product").whereEqualTo("Category","Laptops").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()){
                    for(QueryDocumentSnapshot doc :task.getResult() ){
                        info.add(doc.get("info").toString());
                        Laptop_name.add(doc.get("ProductName").toString());
                        laptopImage.add(doc.get("ProductUri").toString());
                        laptopPrise.add("₹ "+ doc.get("Prise").toString());

                    }
                }
                progressDialog.cancel();

                laptop.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,false));
                laptopAdapter = new laptopAdapter(view.getContext(),Laptop_name,info,laptopImage,laptopPrise);
                laptop.setAdapter(laptopAdapter);



            }
        });


        phoneImage = new ArrayList<>();
        phoneName = new ArrayList<>();
        phoneInfo = new ArrayList<>();
        phonePrise = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Product").whereEqualTo("Category","Phones").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        phoneName.add(doc.get("ProductName").toString()) ;
                        phoneInfo.add(doc.get("info").toString());
                        phoneImage.add((doc.get("ProductUri").toString()));
                        phonePrise.add(doc.get("Prise").toString());

                    }
                }


                phone.setLayoutManager(new LinearLayoutManager(view.getContext() , LinearLayoutManager.HORIZONTAL , false));
                phoneAdapter = new phoneAdapter(view.getContext(),phoneName,phonePrise,phoneImage,phoneInfo); 
                phone.setAdapter(phoneAdapter);
            }
        });
        headphoneImage = new ArrayList<>();
        headphoneName = new ArrayList<>();
        headphoneInfo = new ArrayList<>();
        headphonePrise = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Product").whereEqualTo("Category","headphone").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        headphoneName.add(doc.get("ProductName").toString()) ;
                        headphoneInfo.add(doc.get("info").toString());
                        headphoneImage.add((doc.get("ProductUri").toString()));
                        headphonePrise.add(doc.get("Prise").toString());
                    }
                }
//                Toast.makeText(getContext(), otherName.toString(), Toast.LENGTH_SHORT).show();

                otherAccessories.setLayoutManager(new LinearLayoutManager(view.getContext() , LinearLayoutManager.HORIZONTAL , false));
                headPhoneAdapter = new headPhoneAdapter(view.getContext(),headphoneName,headphonePrise,headphoneImage,headphoneInfo);
                otherAccessories.setAdapter(headPhoneAdapter);
            }
        });

        otheraccessoriesName = new ArrayList<>();
        otheraccessoriesInfo = new ArrayList<>();
        otheraccessoriesImage = new ArrayList<>();
        otheraccessoriesPrise = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Product").whereEqualTo("Category","otherAccessories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        otheraccessoriesName.add(doc.get("ProductName").toString()) ;
                        otheraccessoriesInfo.add(doc.get("info").toString());
                        otheraccessoriesImage.add((doc.get("ProductUri").toString()));
                        otheraccessoriesPrise.add(doc.get("Prise").toString());
                    }
                }
//                Toast.makeText(getContext(), otheraccessoriesName.toString(), Toast.LENGTH_SHORT).show();

                OtherAccessories.setLayoutManager(new LinearLayoutManager(view.getContext() , LinearLayoutManager.HORIZONTAL , false));
                otheraccessoriesAdapter= new otheraccessoriesAdapter(view.getContext(),otheraccessoriesName,otheraccessoriesPrise,otheraccessoriesImage,otheraccessoriesInfo);
                OtherAccessories.setAdapter(otheraccessoriesAdapter);
            }
        });



        return  view ;
    }

    private void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}