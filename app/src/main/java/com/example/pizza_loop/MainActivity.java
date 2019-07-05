package com.example.pizza_loop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    boolean checked = false;
    int length;

    RelativeLayout relat1,relat2;

    EditText editTextEmail;
    EditText editTextPassword;
    EditText ipAddress;

    Handler handler1 = new Handler();
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            relat1.setVisibility(View.VISIBLE);
            relat2.setVisibility(View.VISIBLE);


        }
    };

    Handler handler2 = new Handler();
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            showAlertDialogGetIP(R.layout.getserveraddress);
        }
    };

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relat1 = (RelativeLayout) findViewById(R.id.relat1);
        relat2 = (RelativeLayout) findViewById(R.id.relat2);

        handler1.postDelayed(runnable1,2000);

        Intent intent = getIntent();

        if (!intent.hasExtra("stopPopUp")){
            handler2.postDelayed(runnable2,2500);
        }

    }

    public void login(View view) {
        Button button = findViewById(R.id.login);
        editTextPassword = findViewById(R.id.password);
        editTextEmail = findViewById(R.id.email);

        if (TextUtils.isEmpty(editTextEmail.getText())){
            showAlertDialogValidate(R.layout.checkvalidate,"EMAIL IS EMPTY");
        }
        else if (TextUtils.isEmpty(editTextPassword.getText())){
            showAlertDialogValidate(R.layout.checkvalidate,"PASSWORD IS EMPTY");
        }
        else if (!TextUtils.isEmpty(editTextEmail.getText()) && !TextUtils.isEmpty(editTextPassword.getText())){

            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                if (!checked){

                    if (!DataHolder.getServerIpAddress().isEmpty()) {

                        String request_url = "http://"+DataHolder.getServerIpAddress()+":8080/pizzaApp/findByPassword?password="+editTextPassword.getText();

                        RequestQueue queue = Volley.newRequestQueue(this);

                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                length = response.length();

                                for(int i = 0; i < response.length(); i++){

                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        User.setUserName(jsonObject.get("name").toString());
                                        User.setEmail(jsonObject.get("email").toString());
                                        User.setPhone(Long.parseLong(jsonObject.get("phone").toString()));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });

                        queue.add(jsonArrayRequest);
                        button.setText("LOG IN");
                        checked = true;

                    }
                    else{
                        showAlertDialogGetIP(R.layout.getserveraddress);
                    }

                }
                else {
                    String email = editTextEmail.getText().toString();

                    if (length != 1){
                        showAlertDialogValidate(R.layout.checkvalidate,"ERROR PASSWORD");
                        checked = false;
                        button.setText("CHECK EMAIL");
                    }
                    else{
                        if (email.equals(User.getEmail())) {
                            checked = false;
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);

                        } else {
                            showAlertDialogValidate(R.layout.checkvalidate,"ERROR EMAIL");
                            checked = false;
                            button.setText("CHECK EMAIL");
                        }
                    }
                }

            }
            else{
                Toast toast = Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG);
                toast.show();
            }

        }

    }

    public void signup(View view) {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    public void changePassword(View view) {
        Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void showAlertDialogGetIP(int layout){
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final View layoutView = getLayoutInflater().inflate(layout, null);
        Button getIp = layoutView.findViewById(R.id.getIp);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        getIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipAddress = layoutView.findViewById(R.id.ipaddress);
                DataHolder.setServerIpAddress(ipAddress.getText().toString());
                clearCart();
                alertDialog.dismiss();

            }
        });
    }

    private void showAlertDialogValidate(int layout,String message){
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        Button validate = layoutView.findViewById(R.id.validate);
        TextView textViewCheckValidate = layoutView.findViewById(R.id.textViewCheckValidate);
        textViewCheckValidate.setText(message);
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

    private void clearCart() {
        String request_url = "http://"+DataHolder.getServerIpAddress()+":8080/pizzaApp/deleteCartAll";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url, null, null);
        queue.add(stringRequest);
    }

}
