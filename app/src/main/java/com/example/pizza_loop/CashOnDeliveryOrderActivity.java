package com.example.pizza_loop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class CashOnDeliveryOrderActivity extends AppCompatActivity {

    EditText userName;
    EditText email;
    EditText phone;
    EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_on_delivery_order);

        Intent intent = getIntent();

        if(intent.hasExtra("changeName")){
            TextView textView = findViewById(R.id.tv_login);
            textView.setText("Confirm Your Details");
        }

        userName = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);

        userName.setText(User.getUserName());
        email.setText(User.getEmail());
        phone.setText(Long.toString(User.getPhone()));

        if (intent.hasExtra("address")){
            address.setText(intent.getStringExtra("address"));
        }

    }

    public void submitCashOnDelivery(View view) {
        if (!TextUtils.isEmpty(userName.getText())){
            if (!TextUtils.isEmpty(email.getText())){
                if (!TextUtils.isEmpty(phone.getText())){
                    if (!TextUtils.isEmpty(address.getText())){
                        Toast.makeText(this,"Orderd Successfull",Toast.LENGTH_LONG).show();

                        String request_url = "http://"+DataHolder.getServerIpAddress()+":8080/pizzaApp/deleteCartAll";

                        RequestQueue queue = Volley.newRequestQueue(this);
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url, null, null);
                        queue.add(stringRequest);

                        DataHolder.setCartCount(0);

                        Intent intent = new Intent(CashOnDeliveryOrderActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(this,"ADDRESS EMPTY", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this,"PHONE NUMBER EMPTY", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this,"USER EMAIL EMPTY", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"USER NAME EMPTY", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed(){
        if (DataHolder.getCartCount() >= 1) {
            Intent intent = new Intent(CashOnDeliveryOrderActivity.this, CartActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(CashOnDeliveryOrderActivity.this, PizzaListActivity.class);
            startActivity(intent);
        }
    }

    public void openMap(View view) {
        Intent intent = new Intent(CashOnDeliveryOrderActivity.this, MapActivity.class);
        startActivity(intent);
    }
}
