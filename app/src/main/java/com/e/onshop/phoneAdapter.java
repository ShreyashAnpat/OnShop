package com.e.onshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class phoneAdapter extends RecyclerView.Adapter<phoneAdapter.ViewHolder> {

    Context mcontext ;
    List<String> mphoneName, mphoneImage, mphoneinfo,mphonePrise ;
    LayoutInflater inflater ;

    public phoneAdapter(Context context, ArrayList<String> phoneName, ArrayList<String> phonePrise, ArrayList<String> phoneImage, ArrayList<String> phoneInfo) {
        this.inflater = LayoutInflater.from(context);
        this.mcontext = context ;
        this.mphoneName = phoneName ;
        this.mphoneImage= phoneImage ;
        this.mphoneinfo  = phoneInfo ;
        this.mphonePrise = phonePrise ;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.laptopcard, parent,false);
        return new phoneAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.info.setText(mphoneName.get(position));
            holder.names.setText(mphoneinfo.get(position));
            holder.prise.setText("₹ " +mphonePrise.get(position));
        Picasso.get().load(mphoneImage.get(position)).into(holder.image, new com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }


        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext , description.class);
                intent.putExtra("name", mphoneinfo.get(position));
                mcontext.startActivity(intent);
//                context.startActivity(new Intent(context , description.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mphoneName.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView prise ,names,info;
        ImageView image;
        LinearLayout linearLayout ;
        public ViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.linear_layout);
            names= view.findViewById(R.id.name);
            prise = view.findViewById(R.id.prise);
            info = view.findViewById(R.id.info);
            image = view.findViewById(R.id.image);
            
        }
    }
    
}
