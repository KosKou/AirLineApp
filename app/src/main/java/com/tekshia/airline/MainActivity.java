package com.tekshia.airline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tekshia.airline.Entity.Purchase;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarUsuario(getUsername(),getPassword());
            }
        });
    }

    private void ValidarUsuario(final String username, String password) {
        //TODO poner URL
        String URL = "http://"+ Purchase.Url+"/WSAirline/controller/ClienteController.php?op=1" +
                "&username="+username+"&password="+password;
        //Init WS with Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    String estado = jsonArray.getJSONObject(0).getString("estado");
                    String idUsername = jsonArray.getJSONObject(0).getString("Id");

                    if (estado.contains("success")) {

                        Toast.makeText(MainActivity.this, "BIENVENIDO :D ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, OptionActivity.class);
                        //Elementos enviados
                        intent.putExtra("idUsuario", idUsername);
                        intent.putExtra("username", username);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "VERIFIQUE SUS CREDENCIALES", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error en el codigo broh ", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    //Obtain EditText Data Fields
    private String getUsername(){
        EditText editText = findViewById(R.id.edtUsername);
        return editText.getText().toString();
    }

    private String getPassword(){
        EditText editText = findViewById(R.id.edtPassword);
        return editText.getText().toString();
    }
}
