package com.example.pizza_loop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

public class DetailScreenActivity extends AppCompatActivity {

    Intent intent;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    TextView pToolbarTextView;
    TextView sizeSet, priceSet, quantitySet, totalSet;
    Button button;

    int cartId;
    String pizzaName;
    String pizzaImageUrl;
    float pizzaPrice;
    int pizzaQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);

        intent = getIntent();

        pToolbarTextView = findViewById(R.id.toolbarTitle);
        pizzaName = intent.getStringExtra("name");
        pToolbarTextView.setText(pizzaName);

        ImageView imageView = findViewById(R.id.largeImage);
        button = findViewById(R.id.value);

        sizeSet = findViewById(R.id.sizeSet);
        priceSet = findViewById(R.id.priceSet);
        quantitySet = findViewById(R.id.quantitySet);
        totalSet = findViewById(R.id.totalSet);

        TextView textViewDetails = findViewById(R.id.details);

        pizzaImageUrl = intent.getStringExtra("imageUrl");
        Picasso.get().load(pizzaImageUrl).fit().into(imageView);

        textViewDetails.setText(intent.getStringExtra("description"));

        pizzaPrice = Float.parseFloat(intent.getStringExtra("price"));
        priceSet.setText(Float.toString(pizzaPrice));

        pizzaQuantity = Integer.parseInt(quantitySet.getText().toString());

        totalPrice();

    }

    public void cartDetails(View view) {
        if (DataHolder.getCartCount() < 1){
            showAlertDialogCartEmpty(R.layout.emptycart);
        }
        else{
            Intent intent = new Intent(DetailScreenActivity.this,CartActivity.class);
            startActivity(intent);
        }
    }

    private void showAlertDialogCartEmpty(int layout){
        dialogBuilder = new AlertDialog.Builder(DetailScreenActivity.this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        Button validate = layoutView.findViewById(R.id.button);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void order(View view) {
        cartId = Integer.parseInt(intent.getStringExtra("id"));

        String request_url = "http://"+DataHolder.getServerIpAddress()+":8080/pizzaApp/addCart?pizzaName="+pizzaName+"&pizzaImageUrl="+pizzaImageUrl+"&pizzaPrice="+pizzaPrice+"&pizzaQuantity="+pizzaQuantity;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url, null, null);
        queue.add(stringRequest);

        DataHolder.setCartCount(DataHolder.getCartCount() + 1);

        Intent intent = new Intent(DetailScreenActivity.this, PizzaListActivity.class);
        intent.putExtra("name", "normal pizza");
        startActivity(intent);
    }

    public void minus(View view) {
        int value = Integer.parseInt(button.getText().toString());
        if(value != 1){
            pizzaQuantity = value - 1;
            button.setText(Integer.toString(pizzaQuantity));
            quantitySet.setText(Integer.toString(pizzaQuantity));
            totalPrice();
        }

    }

    public void plus(View view) {
        int value = Integer.parseInt(button.getText().toString());
        pizzaQuantity = value + 1;
        button.setText(Integer.toString(pizzaQuantity));
        quantitySet.setText(Integer.toString(pizzaQuantity));
        totalPrice();
    }

    public void small(View view) {
        sizeSet.setText("Small");
        pizzaPrice = Float.parseFloat(intent.getStringExtra("price"));
        priceSet.setText(Float.toString(pizzaPrice));
        totalPrice();
    }

    public void medium(View view) {
        sizeSet.setText("Medium");
        pizzaPrice = pizzaPrice * 2;
        priceSet.setText(Float.toString(pizzaPrice));
        totalPrice();
    }

    public void large(View view) {
        sizeSet.setText("Large");
        pizzaPrice = pizzaPrice * 3;
        priceSet.setText(Float.toString(pizzaPrice));
        totalPrice();
    }

    private void totalPrice(){
        int quantity = pizzaQuantity;
        float price = pizzaPrice;
        float totalItemPrice = quantity * price;
        totalSet.setText(Float.toString(totalItemPrice));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(DetailScreenActivity.this, PizzaListActivity.class);
        startActivity(intent);
    }

}
