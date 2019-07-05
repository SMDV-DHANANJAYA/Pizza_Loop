package com.example.pizza_loop;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void signup(View view) {
        EditText userName = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText phone = findViewById(R.id.phone);
        EditText password = findViewById(R.id.password);
        EditText reEnterPassword = findViewById(R.id.reEnterPassword);

        if (!TextUtils.isEmpty(userName.getText())){
            if (!TextUtils.isEmpty(email.getText())){
                if (!TextUtils.isEmpty(phone.getText())){
                    if (!TextUtils.isEmpty(password.getText())){
                        if (!TextUtils.isEmpty(reEnterPassword.getText())){
                            if (password.getText().toString().equals(reEnterPassword.getText().toString())){

                                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                                    String request_url = "http://"+DataHolder.getServerIpAddress()+":8080/pizzaApp/addUser?name="+userName.getText()+"&email="+email.getText()+"&phone="+phone.getText()+"&password="+password.getText();

                                    RequestQueue queue = Volley.newRequestQueue(this);
                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url, null, null);
                                    queue.add(stringRequest);

                                    Toast.makeText(this,"Create User Successfull",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                    intent.putExtra("stopPopUp", "true");
                                    startActivity(intent);
                                }
                                else{
                                    Toast toast = Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG);
                                    toast.show();
                                }

                            }
                            else{
                                Toast.makeText(this,"Password Not Match",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(this,"PASSWORD EMPTY", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(this,"PASSWORD EMPTY", Toast.LENGTH_SHORT).show();
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
}
