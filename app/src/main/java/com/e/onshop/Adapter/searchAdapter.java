package com.e.onshop.Adapter;

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

import com.e.onshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder> {
    LayoutInflater inflater;
    Context context ;
    List<String> productName , productPrise, productImage, productInfo;
    public searchAdapter(Context context, ArrayList<String> productName, ArrayList<String> productPrise, ArrayList<String> productImage, ArrayList<String> productInfo) {
        this.context = context ;
        this.productImage  = productImage ;
        this.productName = productName ;
        this.productPrise = productPrise ;
        this.productInfo = productInfo ;
        inflater  = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.search_product,parent,false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Picasso.get().load(productImage.get(position)).placeholder(R.drawable.ic_baseline_image_24).into(holder.imageView);
        holder.Product_name.setText(productName.get(position));
        holder.Prise.setText(productPrise.get(position));
        holder.Info.setText(productInfo.get(position));
        holder.product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , searchAdapter.class);
                intent.putExtra("info", productInfo.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productName.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        TextView  Product_name , Prise , Info ;
        LinearLayout product ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            Product_name = itemView.findViewById(R.id.ProductName);
            Prise = itemView.findViewById(R.id.Prise);
            Info = itemView.findViewById(R.id.info);
            product = itemView.findViewById(R.id.product);
        }
    }
}
