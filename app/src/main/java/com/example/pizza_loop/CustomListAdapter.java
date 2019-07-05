package com.example.pizza_loop;

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

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder> {

    private Context context;
    private List<Pizza> pizza;

    public CustomListAdapter(Context context, List pizza) {
        this.context = context;
        this.pizza = pizza;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(pizza.get(position));

        Pizza pizzaGet = pizza.get(position);

        holder.name.setText(pizzaGet.getName());
        holder.price.setText("Rs: "+Float.toString(pizzaGet.getPrice()));
        Picasso.get().load(pizzaGet.getImageUrl()).resize(500,500).transform(new CropCircleTransformation()).into(holder.imageUrl);

    }

    @Override
    public int getItemCount() {
        return pizza.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView price;
        public ImageView imageUrl;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.pizzaName);
            price = (TextView) itemView.findViewById(R.id.pizzaPrice);
            imageUrl = (ImageView) itemView.findViewById(R.id.pizzaImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Pizza send = (Pizza) view.getTag();

                    String sendId = Integer.toString(send.getPizzaId());
                    String sendPrice = Float.toString(send.getPrice());

                    Intent intent = new Intent(context, DetailScreenActivity.class);
                    intent.putExtra("id", sendId);
                    intent.putExtra("name", send.getName());
                    intent.putExtra("price", sendPrice);
                    intent.putExtra("description", send.getDetails());
                    intent.putExtra("imageUrl", send.getImageUrl());
                    context.startActivity(intent);

                }
            });

        }
    }

}
