package com.e.onshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {

    LayoutInflater inflater ;
    List<String> Category_name ;
    List<Integer> images ;
    Context mcontext ;


    public productAdapter(Context context, ArrayList<String> category_names, ArrayList<Integer> imagelist) {
        this.inflater = LayoutInflater.from(context);
        this.Category_name = category_names ;
        this.images = imagelist ;
        this.mcontext = context ;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.category_card, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String name = Category_name.get(position);
        holder.names.setText(name);


        holder.categoryImage.setImageResource(images.get(position));
        holder.category_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(mcontext, category_acivity.class);
                intent.putExtra("category", name);
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return images.size()   ;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView names ;
        ImageView categoryImage ;
        LinearLayout category_card;
        public ViewHolder(View view) {
            super(view);
            names = view.findViewById(R.id.categoryName);
            categoryImage = view.findViewById(R.id.imageCategory);
            category_card = view.findViewById(R.id.category_card);
        }
    }
}
