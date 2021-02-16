package com.e.onshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.ContentValues.TAG;

public class cardProductAdapter extends RecyclerView.Adapter<cardProductAdapter.ViewHolder> {

    Context context ;
    List<String> ProductName ,ProductImage ;
    List<String> ProductPrise;
    List<Double> TotalPrise ;
    ProgressDialog pd;
    String auth ;
    LayoutInflater layoutInflater ;

    public cardProductAdapter(Context context, List<String> productName, List<String> productImage, List<String> productPrises, List<Double> totalPrises, String userid) {
        this.context = context ;
        this.ProductImage = productImage ;
        this.ProductName = productName ;
        this.ProductPrise = productPrises ;
        this.TotalPrise = totalPrises ;
        this.layoutInflater = LayoutInflater.from(context);
        this.auth =userid ;
      this.pd = new ProgressDialog(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.add_product_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Picasso.get().load(ProductImage.get(position)).into(holder.image);
        holder.name.setText(ProductName.get(position));
        holder.prise.setText("Prise : "+ TotalPrise.get(position).toString() + " ₹");
        holder.peace.setText("Peace : " + ProductPrise.get(position).toString());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Deleting Product");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        pd.show();
                        FirebaseFirestore.getInstance().collection("Add-Card").whereEqualTo("ProductName", ProductName.get(position)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isComplete()){
                                    for (DocumentChange doc : task.getResult().getDocumentChanges()){
                                        if (doc.getType()== DocumentChange.Type.ADDED ){
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, ProductName.size());
                                            FirebaseFirestore.getInstance().collection("Add-Card").document(doc.getDocument().getId()).delete();
                                            ProductImage.remove(position);
                                            ProductName.remove(position);
                                            ProductPrise.remove(position);
                                            TotalPrise.remove(position);
                                            pd.cancel();
                                        }
                                    }

                                }
                            }
                        });



            }
        });

    }

    @Override
    public int getItemCount() {
        return ProductName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image , delete ;
        TextView name , prise , peace ;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.productImage);
            name = view.findViewById(R.id.ProductName);
            prise = view.findViewById(R.id.Prise);
            peace =view.findViewById(R.id.Price);
            delete = view.findViewById(R.id.delete);
        }
    }
}
