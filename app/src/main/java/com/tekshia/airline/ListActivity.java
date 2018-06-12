package com.tekshia.airline;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.github.snowdream.android.widget.SmartImageView;
import com.tekshia.airline.Adapters.PurchaseAdapter;
import com.tekshia.airline.Entity.Purchase;

import org.json.JSONArray;

import java.security.Principal;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    //Usuario
    private String IdUser;
    private String Username;
    //Pantalla Principal
    private TextView lblbienvenida;
    private Spinner spnAirline;
    private ListView LSTVW;
    //Spinner de Aerolineas
    private ArrayList idAirline  = new ArrayList();
    private ArrayList nameAirline = new ArrayList();
    //Destinos
    private ArrayList desId  = new ArrayList();
    private ArrayList destination = new ArrayList();
    private ArrayList desDate = new ArrayList();
    private ArrayList desPrice = new ArrayList();
    private ArrayList desTipo = new ArrayList();
    private ArrayList desAirline = new ArrayList();
    private ArrayList desImage = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);
        //Inicializamos actions
        lblbienvenida = (TextView)findViewById(R.id.lblBienvenida);
        spnAirline = (Spinner)findViewById(R.id.spnAirline);
        LSTVW = (ListView) findViewById(R.id.LSTVW);

        SpinnerData();
        //idUsuario y username
        IdUser = getIntent().getStringExtra("idUsuario");
        Username = getIntent().getStringExtra("username");
        lblbienvenida.setText("Bienvenido a la Tienda \n Usuario : "+Username);
    }

    private void SpinnerData() {
        idAirline.clear();
        nameAirline.clear();

        //TODO Poner URL
        String URL = "http://"+ Purchase.Url+"/WSAirline/controller/AirlineController.php?op=1";
        //Init WS with Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0; i<jsonArray.length(); i++) {
                        idAirline.add(jsonArray.getJSONObject(i).getString("Id"));
                        nameAirline.add(jsonArray.getJSONObject(i).getString("Aerolinea"));
                    }
                    spnAirline.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, nameAirline));
                    spnAirline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            //Cargar Elementos
                            String airlineCode = String.valueOf(idAirline.get(i));
                            CargarListView(Integer.parseInt(airlineCode));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            CargarListView(1);
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(ListActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListActivity.this, "Error en el codigo broh ", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void CargarListView(int code) {

        desId.clear();
        destination.clear();
        desDate.clear();
        desPrice.clear();
        desTipo.clear();
        desAirline.clear();
        //TODO colocar URL
        String URL = "http://"+ Purchase.Url+"/WSAirline/controller/DestinationController.php?op=2&id="+code;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0; i<jsonArray.length(); i++) {

                        desId.add(jsonArray.getJSONObject(i).getString("Id"));
                        destination.add(jsonArray.getJSONObject(i).getString("Destino"));
                        desDate.add(jsonArray.getJSONObject(i).getString("Fecha"));
                        desPrice.add(jsonArray.getJSONObject(i).getString("Precio"));
                        desTipo.add(jsonArray.getJSONObject(i).getString("Tipo"));
                        desAirline.add(jsonArray.getJSONObject(i).getString("Aerolinea"));
                        desImage.add(jsonArray.getJSONObject(i).getString("Image"));

                    }

                    LSTVW.setAdapter(new PurchaseAdapter(getApplicationContext(),desId,destination,
                            desDate,desPrice,desTipo,desAirline,desImage,IdUser));

                }catch (Exception ex)
                {
                    Toast.makeText(ListActivity.this, "ERROR: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListActivity.this, "ERROR DE CODIGO", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
}
