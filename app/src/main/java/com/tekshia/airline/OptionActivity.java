package com.tekshia.airline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OptionActivity extends AppCompatActivity {
    //Usuario
    private String IdUser;
    private String Username;

    private TextView lblWelcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        //Inicializamos actions
        lblWelcome = (TextView)findViewById(R.id.lblWelcome);

        //idUsuario y username
        IdUser = getIntent().getStringExtra("idUsuario");
        Username = getIntent().getStringExtra("username");
        lblWelcome.setText("Bienvenido a la Tienda \n Usuario : "+Username);

//        Options
        TextView purchase   =   findViewById(R.id.purchase);
        TextView purchaseds =   findViewById(R.id.purchaseds);

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent purchaseIntent = new Intent(OptionActivity.this, ListActivity.class);
                //Elementos enviados
                purchaseIntent.putExtra("idUsuario", IdUser);
                purchaseIntent.putExtra("username", Username);
                startActivity(purchaseIntent);
            }
        });

        purchaseds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent purchasedIntent = new Intent(OptionActivity.this, PurchasedActivity.class);
                //Elementos enviados
                purchasedIntent.putExtra("idUsuario", IdUser);
                purchasedIntent.putExtra("username", Username);
                startActivity(purchasedIntent);
            }
        });
    }
}
