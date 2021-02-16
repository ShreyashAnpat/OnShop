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

public class headPhoneAdapter extends RecyclerView.Adapter<headPhoneAdapter.ViewHolder> {

    LayoutInflater inflater ;
    Context context ;
    List<String> names, image, prise, info ;

    public headPhoneAdapter(Context context, ArrayList<String> otherName, ArrayList<String> otherPrise, ArrayList<String> otherImage, ArrayList<String> otherInfo) {
        this.inflater = LayoutInflater.from(context);
        this.names = otherName;
        this.image = otherImage ;
        this.info = otherInfo ;
        this.prise = otherPrise ;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.laptopcard, parent,false);
        return new headPhoneAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.names.setText(info.get(position));
        holder.info.setText(names.get(position));
        holder.prise.setText("₹ "+prise.get(position));


        Picasso.get().load(image.get(position)).into(holder.image, new com.squareup.picasso.Callback(){

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
                Intent intent = new Intent(context , description.class);
                intent.putExtra("name", info.get(position));
                context.startActivity(intent);
//                context.startActivity(new Intent(context , description.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return names.size();
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
