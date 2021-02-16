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

public class laptopAdapter extends RecyclerView.Adapter<laptopAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater ;
    private List<String> image, name ,prise , info ;



    public laptopAdapter(final Context context, ArrayList<String> laptopPrise, ArrayList<String> info, ArrayList<String> laptopImage, ArrayList<String> laptop_name) {
        this.context = context ;
        this.layoutInflater = LayoutInflater.from(context);
        this.name = laptopPrise ;
        this.info = info ;
        this.prise = laptop_name;
        this.image = laptopImage ;



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =layoutInflater.inflate(R.layout.laptopcard, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.info.setText(name.get(position));
        holder.names.setText(info.get(position));
        holder.prise.setText(prise.get(position));

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
    return name.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
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
