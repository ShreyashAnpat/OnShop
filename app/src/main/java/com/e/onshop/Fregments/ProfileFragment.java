package com.e.onshop.Fregments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.e.onshop.MainActivity;
import com.e.onshop.R;
import com.e.onshop.wellcome;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.checkPermission;


public class ProfileFragment extends Fragment {
    TextView username, phonenumber, locationmain;
    CircleImageView profile;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    int PICK_IMAGE = 1, RESULT_OK = -1;
    Uri imageUri;
    Uri url;
    String uris;
    StorageReference Imagename, Folder;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double latitude ;
    Double longitude ;
    Button logout ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        locationmain = view.findViewById(R.id.location);
        username = view.findViewById(R.id.username);
        phonenumber = view.findViewById(R.id.phone_number);
        profile = view.findViewById(R.id.profile_image);
        logout = view.findViewById(R.id.logout);

        Folder = FirebaseStorage.getInstance().getReference().child("Image");
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "sellect profile"), PICK_IMAGE);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(view.getContext() , wellcome.class));
            }
        });

        final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("user").document(auth.getCurrentUser().getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    username.setText(documentSnapshot.getString("name"));
                    phonenumber.setText(documentSnapshot.getString("phone_no"));
                    uris = documentSnapshot.getString("imgUrl");
                    locationmain.setText(documentSnapshot.getString("address "));
                    profile.setImageURI(Uri.parse(uris));
                    url = Uri.parse(uris);
    
                        Picasso.get().load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(profile, new Callback() {
    
                            @Override
                            public void onSuccess() {
    
                            }
    
                            @Override
                            public void onError(Exception e) {
    
                            }
    
    
                        });
//
                }
            }
        });


        profile.setImageURI(url);


        return (view);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Adding Products");
            progressDialog.show() ;
            imageUri = data.getData();
            Imagename = Folder.child(auth.getCurrentUser().getUid() + imageUri.getLastPathSegment());
            Imagename.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                    public void onSuccess(final Uri uri) {

                                    DocumentReference image = FirebaseFirestore.getInstance().collection("user").document(auth.getCurrentUser().getUid());
                                    HashMap<String ,Object > hashMap = new HashMap<>();
                                    hashMap.put("imgUrl",String.valueOf(uri));
                                    image.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            profile.setImageURI(imageUri);
                                        }
                                    });

                                    progressDialog.cancel();
                                }
                            });
                        }
                    });
                }



        }
    }
