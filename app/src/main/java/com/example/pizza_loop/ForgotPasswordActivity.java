package com.example.pizza_loop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ForgotPasswordActivity extends AppCompatActivity {

    boolean checked = false;
    int length;
    String request_url;

    Button button;
    EditText email;
    EditText password;
    EditText reEnterPassword;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void updatePassword(View view) {
        button = findViewById(R.id.Update);
        email = findViewById(R.id.emailCheck);
        password = findViewById(R.id.password);
        reEnterPassword = findViewById(R.id.reEnterPassword);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            if (!TextUtils.isEmpty(email.getText())){
                if (!TextUtils.isEmpty(password.getText())){
                    if (!TextUtils.isEmpty(reEnterPassword.getText())){
                        if (password.getText().toString().equals(reEnterPassword.getText().toString())){

                            if (!checked){
                                request_url = "http://"+DataHolder.getServerIpAddress()+":8080/pizzaApp/findByEmail?email="+email.getText();

                                queue = Volley.newRequestQueue(this);

                                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        for(int i = 0; i < response.length(); i++){

                                            length = response.length();

                                            try {
                                                JSONObject jsonObject = response.getJSONObject(i);

                                                User.setEmail(jsonObject.get("email").toString());

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
                                button.setText("CHANGE");
                                checked = true;
                            }
                            else{
                                if (length == 1){
                                    request_url = "http://"+DataHolder.getServerIpAddress()+":8080/pizzaApp/updateUser?email="+email.getText()+"&password="+password.getText();

                                    queue = Volley.newRequestQueue(this);
                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, request_url, null, null);
                                    queue.add(stringRequest);

                                    Toast.makeText(this,"Update Password Successfull",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                                    intent.putExtra("stopPopUp", "true");
                                    startActivity(intent);
                                }
                                else{
                                    showAlertDialogValidate(R.layout.checkvalidate,"EMAIL NOT MATCH");
                                }
                            }
                        }
                        else{
                            showAlertDialogValidate(R.layout.checkvalidate,"PASSWORD NOT MATCH");
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
                Toast.makeText(this,"EMAIL EMPTY", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast toast = Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void showAlertDialogValidate(int layout,String message){
        dialogBuilder = new AlertDialog.Builder(ForgotPasswordActivity.this);
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
}
