package com.tekshia.airline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tekshia.airline.Adapters.PurchaseAdapter;
import com.tekshia.airline.Entity.Purchase;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PurchasedActivity extends AppCompatActivity {
//    Datos de Usuario
    private String IdUser;
    private String Username;
    //Vuelos Comprados
    private ArrayList desId  = new ArrayList();
    private ArrayList destination = new ArrayList();
    private ArrayList desDate = new ArrayList();
    private ArrayList desPrice = new ArrayList();
    private ArrayList desTipo = new ArrayList();
    private ArrayList desAirline = new ArrayList();
    private ArrayList desImage = new ArrayList();

    //Lista
    private ListView LSTVW;
    //Objetos sobrantes
    private TextView welcome;
    private Spinner spinner;
    //Obtener Vuelos Comprados
    private ArrayList purId = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);

//        Inicializa Lista:
        LSTVW = (ListView) findViewById(R.id.LSTVW);
        //idUsuario y username
        IdUser = getIntent().getStringExtra("idUsuario");
        Username = getIntent().getStringExtra("username");
        //Objetos Sobrantes
        welcome = findViewById(R.id.lblBienvenida);
        welcome.setText("Bienvenido Usuario: "+Username+"\n Sus vuelos son: ");
        spinner = findViewById(R.id.spnAirline);
        spinner.setVisibility(View.GONE);
        //Listamos Las compras del cliente
        getPurchases(IdUser);
    }
//    Listar Compras por Cliente
    private void getPurchases(String idUser) {
        desId.clear();
        destination.clear();
        desDate.clear();
        desPrice.clear();
        desTipo.clear();
        desAirline.clear();
        desImage.clear();
        //Colocar URL del WebService
        String URL = "http://"+ Purchase.Url+"/WSAirline/controller/PurchaseController.php?op=3&id="+idUser;
        //Obtiene datos de respuesta
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Llena el JSON obtenido del WS
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i<jsonArray.length(); i++){
//                        Vuelos
                        desId.add(jsonArray.getJSONObject(i).getString("IdDestino"));
                        destination.add(jsonArray.getJSONObject(i).getString("Destino"));
                        desDate.add(jsonArray.getJSONObject(i).getString("Date"));
                        desPrice.add(jsonArray.getJSONObject(i).getString("Price"));
                        desTipo.add(jsonArray.getJSONObject(i).getString("Type"));
                        desAirline.add(jsonArray.getJSONObject(i).getString("Airline"));
                        desImage.add(jsonArray.getJSONObject(i).getString("Image"));
                    }
                    //Listamos los elementos
                    PurchaseAdapter purchaseAdapter = new PurchaseAdapter(getApplicationContext(),desId,destination,
                            desDate,desPrice,desTipo,desAirline,desImage,IdUser);
                    //Que no se muestre el boton
                    purchaseAdapter.setBtnMode(false);
                    LSTVW.setAdapter(purchaseAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PurchasedActivity.this, "ERROR DE CODIGO", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}
