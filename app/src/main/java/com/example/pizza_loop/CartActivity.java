package com.example.pizza_loop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    String request_url;

    RequestQueue queue;

    List<Cart> cart;

    RecyclerView recyclerView;
    RecyclerView.Adapter pAdapter;
    RecyclerView.LayoutManager layoutManager;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        request_url = "http://"+DataHolder.getServerIpAddress()+":8080/pizzaApp/cartAll";

        queue = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        cart = new ArrayList<>();

        sendRequest();

        Toast.makeText(this,"Press and Hold to Remove Item",Toast.LENGTH_LONG).show();

    }

    private void sendRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){

                    Cart cartRequest = new Cart();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        cartRequest.setCartId(Integer.parseInt(jsonObject.get("cartId").toString()));
                        cartRequest.setPizzaName(jsonObject.get("pizzaName").toString());
                        cartRequest.setPizzaPrice(Float.parseFloat(jsonObject.get("pizzaPrice").toString()));
                        cartRequest.setPizzaImageurl(jsonObject.get("pizzaImageUrl").toString());
                        cartRequest.setPizzaQuantity(Integer.parseInt(jsonObject.get("pizzaQuantity").toString()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cart.add(cartRequest);

                }

                pAdapter = new CustomCartAdapter(CartActivity.this, cart);
                recyclerView.setAdapter(pAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);
    }

    public void submit(View view) {
        showAlertDialogValidate(R.layout.submitorder);
    }

    private void showAlertDialogValidate(int layout){
        final boolean[] cashOnDelivery = {true};

        dialogBuilder = new AlertDialog.Builder(CartActivity.this);
        View layoutView = getLayoutInflater().inflate(layout, null);

        Button submit = layoutView.findViewById(R.id.submitOrder);
        final Switch cashOnDeliverySwitch = layoutView.findViewById(R.id.cashOnDeliverySwitch);
        final Switch onlinePaymentButton = layoutView.findViewById(R.id.onlinePaymentButton);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cashOnDeliverySwitch.setChecked(true);

        alertDialog.show();

        cashOnDeliverySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashOnDelivery[0] = true;
                onlinePaymentButton.setChecked(false);
            }
        });

        onlinePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashOnDelivery[0] = false;
                cashOnDeliverySwitch.setChecked(false);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (cashOnDelivery[0]){
                    intent = new Intent(CartActivity.this, CashOnDeliveryOrderActivity.class);
                }
                else{
                    intent = new Intent(CartActivity.this, OnlineOrderActivity.class);
                }
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(CartActivity.this, PizzaListActivity.class);
        startActivity(intent);
    }
}
