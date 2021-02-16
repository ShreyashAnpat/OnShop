package com.e.onshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.e.onshop.R;
import com.e.onshop.description;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder>  {

    private Context context;
    private LayoutInflater layoutInflater ;
    private List<String> productImage;
    private List<String> productName;
    private List<String> productWeight;
    private List<Object> productInfo;
    private List<String> productPrise ;



    public categoryAdapter(Context context, ArrayList<String> productImage, ArrayList<String> productName, ArrayList<String> productPrise, ArrayList<String> productWeight, List<Object> productInfo) {
       this.context =context ;
        this.productPrise =productPrise;
        this.productWeight = productWeight;
        this.productName = productName ;
        this.layoutInflater =LayoutInflater.from(context);
        this.productImage = productImage ;
        this.productInfo = productInfo ;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =layoutInflater.inflate(R.layout.filter_products_card, parent,false);
    return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.prise.setText(productPrise.get(position) );
        holder.weight.setText(" Rs/"+ productWeight.get(position));
        holder.names.setText(productName.get(position));

        Picasso.get().load(productImage.get(position)).into(holder.image, new com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }



        });
        holder.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(context, productName.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context , description.class);
                intent.putExtra("name",productInfo.get(position).toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
      return
              productPrise.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView prise ,names,weight;
        ImageView image;
        CardView cardView ;
        Button addProduct ;
        public ViewHolder(View view) {
            super(view);
            addProduct = view.findViewById(R.id.addProduct);
            image= view.findViewById(R.id.imageCategory);
            names = view.findViewById(R.id.name);
            weight = view.findViewById(R.id.weights);
            prise = view.findViewById(R.id.prise);

        }
    }
}
