package com.example.pizza_loop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OnlineOrderActivity extends AppCompatActivity {

    EditText cartName;
    EditText cartNumber;
    EditText cvv;
    EditText expiryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_order);

        cartName = findViewById(R.id.cartName);
        cartNumber = findViewById(R.id.cartNumber);
        cvv = findViewById(R.id.cvv);
        expiryDate = findViewById(R.id.expiryDate);
    }

    public void submitOnlineOrder(View view) {
        if (!TextUtils.isEmpty(cartName.getText())){
            if (!TextUtils.isEmpty(cartNumber.getText())){
                if (!TextUtils.isEmpty(cvv.getText())){
                    if (!TextUtils.isEmpty(expiryDate.getText())){
                        Intent intent = new Intent(OnlineOrderActivity.this, CashOnDeliveryOrderActivity.class);
                        intent.putExtra("changeName","Online");
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(this,"CART EXPIRY DATE EMPTY", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this,"CART CVV EMPTY", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this,"CART NUMBER EMPTY", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"CART NAME EMPTY", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed(){
        if (DataHolder.getCartCount() >= 1) {
            Intent intent = new Intent(OnlineOrderActivity.this, CartActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(OnlineOrderActivity.this, PizzaListActivity.class);
            startActivity(intent);
        }
    }
}
