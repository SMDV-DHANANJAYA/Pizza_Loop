package com.example.pizza_loop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView pToolbarTextView;
    RecyclerView pRecyclerView;
    List< Pizza > pPizzaList;
    Pizza pPizzaData;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pToolbarTextView = findViewById(R.id.toolbarTitle);
        pToolbarTextView.setText(getResources().getString(R.string.title_1));

        pRecyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(HomeActivity.this, 2);
        pRecyclerView.setLayoutManager(mGridLayoutManager);

        pPizzaList = new ArrayList<>();

        pPizzaData = new Pizza("Normal Pizza",R.drawable.normal_pizza);
        pPizzaList.add(pPizzaData);

        pPizzaData = new Pizza("Vegitable Pizza",R.drawable.vegi_pizza);
        pPizzaList.add(pPizzaData);

        pPizzaData = new Pizza("Cups",R.drawable.cup);
        pPizzaList.add(pPizzaData);

        pPizzaData = new Pizza("Others",R.drawable.biscuit_pizza);
        pPizzaList.add(pPizzaData);

        CustomAdapter customAdapter = new CustomAdapter(HomeActivity.this, pPizzaList);
        pRecyclerView.setAdapter(customAdapter);

    }

    public void cartHome(View view) {
        if (DataHolder.getCartCount() < 1){
            showAlertDialogCartEmpty(R.layout.emptycart);
        }
        else{
            Intent intent = new Intent(HomeActivity.this,CartActivity.class);
            startActivity(intent);
        }

    }

    private void showAlertDialogCartEmpty(int layout){
        dialogBuilder = new AlertDialog.Builder(HomeActivity.this);
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
    public void onBackPressed(){}

}
