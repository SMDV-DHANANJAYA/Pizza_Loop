package com.example.pizza_loop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class CustomCartAdapter extends RecyclerView.Adapter<CustomCartAdapter.ViewHolder> {

    private Context context;
    private List<Cart> cart;


    public CustomCartAdapter(Context context, List cart) {
        this.context = context;
        this.cart = cart;
    }

    @Override
    public CustomCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(cart.get(position));

        Cart cartGet = cart.get(position);

        holder.name.setText(cartGet.getPizzaName());

        int quantity = cartGet.getPizzaQuantity();
        float price = cartGet.getPizzaPrice();
        float totalPrice = quantity * price;

        holder.quantity.setText(Integer.toString(quantity));
        holder.price.setText(Float.toString(price));
        holder.totalPrice.setText(Float.toString(totalPrice));
        Picasso.get().load(cartGet.getPizzaImageurl()).resize(500,500).transform(new CropCircleTransformation()).into(holder.imageUrl);

    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView price;
        public TextView quantity;
        public TextView totalPrice;
        public ImageView imageUrl;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.pizzaName);
            price = (TextView) itemView.findViewById(R.id.pizzaPrice);
            quantity = (TextView) itemView.findViewById(R.id.pizzaQuantity);
            totalPrice = (TextView) itemView.findViewById(R.id.totalPrice);
            imageUrl = (ImageView) itemView.findViewById(R.id.pizzaImage);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Cart send = (Cart) view.getTag();

                    String sendId = Integer.toString(send.getCartId());

                    String request_url = "http://"+ DataHolder.getServerIpAddress()+":8080/pizzaApp/deleteByCartId?id="+sendId;

                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url, null, null);
                    queue.add(stringRequest);

                    if (cart.size() <= 1) {
                        cart.remove(getAdapterPosition());
                        notifyDataSetChanged();
                        DataHolder.setCartCount(DataHolder.getCartCount() - 1);
                        Intent intent = new Intent(context, PizzaListActivity.class);
                        context.startActivity(intent);
                    }
                    else{
                        cart.remove(getAdapterPosition());
                        notifyDataSetChanged();
                        DataHolder.setCartCount(DataHolder.getCartCount() - 1);
                    }

                    return false;
                }

                /*@Override
                public void onClick(View view) {
                    Cart send = (Cart) view.getTag();

                    String sendId = Integer.toString(send.getCartId());

                    Toast.makeText(context,sendId,Toast.LENGTH_SHORT).show();

                    //String request_url = "http://"+serverIpAddress+":8080/pizzaApp/deleteByCartId?id="+sendId;
                    String request_url = "http://10.0.2.2:8080/pizzaApp/deleteByCartId?id="+sendId;

                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url, null, null);
                    queue.add(stringRequest);

                    Intent intent = new Intent(context,CartActivity.class);
                    context.startActivity(intent);
                }*/
            });

        }
    }

}
