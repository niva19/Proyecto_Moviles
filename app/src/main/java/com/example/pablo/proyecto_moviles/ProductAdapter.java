package com.example.pablo.proyecto_moviles;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;


    //we are storing all the products in a list
    private List<Product> productList;

    //getting the context and product list with constructor
    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_products, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        final Product product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getNombre());
        holder.textViewShortDesc.setText(product.getServicio());
        holder.textViewRating.setText(String.valueOf(4.3));
        holder.textViewPrice.setText(product.getCorreo());

        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.pedro));

        Picasso.with(mCtx).load(product.getImage()).placeholder(R.drawable.default1)
                .error(R.drawable.default1)
                .into(holder.imageView, new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

        holder.imageView.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View arg0) {
                VariablesGlobales vg = VariablesGlobales.getInstance();
                vg.setId(product.getId());
                Intent intento = new Intent(mCtx, Empresa_Info.class);
                mCtx.startActivity(intento);

            }

        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
