package com.e.onshop;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
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

public class otheraccessoriesAdapter extends RecyclerView.Adapter<otheraccessoriesAdapter.ViewHolder> {

    LayoutInflater inflater ;
    Context mcontext ;
    List<String>  name, image, prise, info ;

    public otheraccessoriesAdapter(Context context, ArrayList<String> otheraccessoriesName, ArrayList<String> otheraccessoriesPrise, ArrayList<String> otheraccessoriesImage, ArrayList<String> otheraccessoriesInfo) {

        inflater = LayoutInflater.from(context);
        mcontext = context ;
        name = otheraccessoriesName ;
        image = otheraccessoriesImage ;
        info = otheraccessoriesInfo ;
        prise = otheraccessoriesPrise ;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.laptopcard, parent,false);
        return new otheraccessoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.info.setText(name.get(position));
        holder.names.setText(info.get(position));
        holder.prise.setText("₹ "+ prise.get(position));

//        holder.prise.setText(prise.get(position), TextView.BufferType.SPANNABLE);
//        Spannable spannable = (Spannable) holder.prise.getText();
//        spannable.setSpan(new StrikethroughSpan(), 0, prise.get(position).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
                Intent intent = new Intent(mcontext , description.class);
                intent.putExtra("name", info.get(position));
                mcontext.startActivity(intent);
//                context.startActivity(new Intent(context , description.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return name.size();
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
