package com.tekshia.airline;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.snowdream.android.widget.SmartImageView;
import com.tekshia.airline.Entity.Purchase;

public class PurchaseActivity extends AppCompatActivity {

    private String IdCliente;
    private String IdDestino;
    private String Destino;
    private String Fecha;
    private String Price;
    private String Tipo;
    private String Airline;
    private String Image;

    private TextView txtCodigo,txtDestino,txtFecha,txtPrecio,txtTipo,txtAerolinea,txtMessage;
    private SmartImageView smartImage;
    private Button btnComprar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadapter);

        IdCliente = getIntent().getStringExtra("IdCliente");
        IdDestino = getIntent().getStringExtra("IdDestino");
        Destino = getIntent().getStringExtra("Destino");
        Fecha = getIntent().getStringExtra("Fecha");
        Price = getIntent().getStringExtra("Price");
        Tipo = getIntent().getStringExtra("Tipo");
        Airline = getIntent().getStringExtra("Airline");
        Image = getIntent().getStringExtra("Image");

        //Iniciar componentes
        txtCodigo = findViewById(R.id.lblCodigo);
        txtDestino = findViewById(R.id.lblDestino);
        txtFecha = findViewById(R.id.lblFecha);
        txtPrecio = findViewById(R.id.lblPrecio);
        txtTipo = findViewById(R.id.lblTipo);
        txtAerolinea = findViewById(R.id.lblAerolinea);
        txtMessage = findViewById(R.id.txMessage);
        smartImage = findViewById(R.id.productImage);

        btnComprar = findViewById(R.id.btnComprar);

        String imageURL = "http://"+ Purchase.Url+"/WSAirline/images/"+Image+".jpg";
        Rect rect = new Rect(smartImage.getLeft(),smartImage.getTop(),smartImage.getRight(),smartImage.getBottom());

        //Colocamos Valores
        smartImage.setImageUrl(imageURL, rect);
        txtCodigo.setText("Codigo: "+IdDestino);
        txtDestino.setText("Destino: "+Destino);
        txtFecha.setText("Fecha: "+Fecha);
        txtPrecio.setText("Precio: "+Price);
        txtTipo.setText("Tipo: "+Tipo);
        txtAerolinea.setText("Aerolinea: "+Airline);

        //Usuarios
        txtMessage.setText("Usuario: "+IdCliente);

        //Comprar
        btnComprar.setText("Confirma Compra");

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comprar(IdCliente, IdDestino);
            }
        });

    }

    private void Comprar(String idCliente, String idDestino) {
        //TODO Colocar URL
        String URL = "http://"+ Purchase.Url+"/WSAirline/controller/PurchaseController.php?op=2" +
                "&idCliente="+idCliente+"&idDestino="+idDestino;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String respuesta = String.valueOf(response);
                    if (respuesta.contains("1")) {
                        Toast.makeText(PurchaseActivity.this, "SE REGISTRO LA COMPRA CON EXITO :)", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PurchaseActivity.this, "NO SE PUDO REALIZAR LA OPERACIÓN", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PurchaseActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PurchaseActivity.this, "ERROR DE CODIGO", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}
