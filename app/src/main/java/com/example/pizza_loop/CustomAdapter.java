package com.example.pizza_loop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter< PizzaViewHolder > {

    private Context pContext;
    private List<Pizza> pPizzaList;


    CustomAdapter(Context pContext, List< Pizza > pPizzaList) {
        this.pContext = pContext;
        this.pPizzaList = pPizzaList;
    }

    @Override
    public PizzaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View pView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_view_item, parent, false);
        return new PizzaViewHolder(pView);
    }

    @Override
    public void onBindViewHolder(final PizzaViewHolder holder, int position) {

        holder.pImage.setImageResource(pPizzaList.get(position).getPizzaImage());
        holder.pTitle.setText(pPizzaList.get(position).getName());

        holder.pCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = pPizzaList.get(holder.getAdapterPosition()).getName();
                if (itemName.equalsIgnoreCase("normal pizza")) {
                    DataHolder.setName(itemName);
                    Intent pIntent = new Intent(pContext, PizzaListActivity.class);
                    pContext.startActivity(pIntent);
                }
                else{
                    Toast.makeText(view.getContext(), "Comming Soon..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pPizzaList.size();
    }
}

class PizzaViewHolder extends RecyclerView.ViewHolder {

    ImageView pImage;
    TextView pTitle;
    CardView pCardView;

    PizzaViewHolder(View itemView) {
        super(itemView);

        pImage = itemView.findViewById(R.id.image);
        pTitle = itemView.findViewById(R.id.title);

        pCardView = itemView.findViewById(R.id.cardview);
    }

}
