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
import android.widget.TextView;

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

public class PizzaListActivity extends AppCompatActivity {

    TextView pToolbarTextView;
    RecyclerView recyclerView;
    RecyclerView.Adapter pAdapter;
    RecyclerView.LayoutManager layoutManager;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    List<Pizza> pizza;

    RequestQueue queue;

    String request_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_list);

        request_url = "http://"+DataHolder.getServerIpAddress()+":8080/pizzaApp/pizzaAll";

        pToolbarTextView = findViewById(R.id.toolbarTitle);
        pToolbarTextView.setText(DataHolder.getName());

        queue = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        pizza = new ArrayList<>();

        sendRequest();
    }

    public void sendRequest(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){

                    Pizza pizzaRequest = new Pizza();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        pizzaRequest.setPizzaId(Integer.parseInt(jsonObject.get("pizzaId").toString()));
                        pizzaRequest.setName(jsonObject.get("name").toString());
                        pizzaRequest.setPrice(Float.parseFloat(jsonObject.get("price").toString()));
                        pizzaRequest.setImageUrl(jsonObject.get("imageUrl").toString());
                        pizzaRequest.setDetails(jsonObject.get("description").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    pizza.add(pizzaRequest);

                }

                pAdapter = new CustomListAdapter(PizzaListActivity.this, pizza);
                recyclerView.setAdapter(pAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);

    }

    public void cartList(View view) {
        if (DataHolder.getCartCount() < 1){
            showAlertDialogCartEmpty(R.layout.emptycart);
        }
        else{
            Intent intent = new Intent(PizzaListActivity.this,CartActivity.class);
            startActivity(intent);
        }
    }

    private void showAlertDialogCartEmpty(int layout){
        dialogBuilder = new AlertDialog.Builder(PizzaListActivity.this);
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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(PizzaListActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
