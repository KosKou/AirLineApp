package com.tekshia.airline.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;
import com.tekshia.airline.Entity.Purchase;
import com.tekshia.airline.PurchaseActivity;
import com.tekshia.airline.R;

import java.util.ArrayList;

public class PurchaseAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    //Agregados
    TextView txtCodigo,txtDestino,txtFecha,txtPrecio,txtTipo,txtAerolinea;
    SmartImageView smartImage;
    Button btnComprar;
    String IdUser;

    private ArrayList desId  = new ArrayList();
    private ArrayList destination = new ArrayList();
    private ArrayList desDate = new ArrayList();
    private ArrayList desPrice = new ArrayList();
    private ArrayList desTipo = new ArrayList();
    private ArrayList desAirline = new ArrayList();
    private ArrayList desImage = new ArrayList();

    public PurchaseAdapter(Context context, ArrayList desId, ArrayList destination,
                           ArrayList desDate, ArrayList desPrice, ArrayList desTipo,
                           ArrayList desAirline, ArrayList desImage, String IdUser) {
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Own
        this.desId = desId;
        this.destination = destination;
        this.desDate = desDate;
        this.desPrice = desPrice;
        this.desTipo = desTipo;
        this.desAirline = desAirline;
        this.desImage = desImage;
        this.IdUser = IdUser;
    }

    @Override
    public int getCount() {
        return destination.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.listadapter,null);

        txtCodigo = viewGroup.findViewById(R.id.lblCodigo);
        txtDestino = viewGroup.findViewById(R.id.lblDestino);
        txtFecha = viewGroup.findViewById(R.id.lblFecha);
        txtPrecio = viewGroup.findViewById(R.id.lblPrecio);
        txtTipo = viewGroup.findViewById(R.id.lblTipo);
        txtAerolinea = viewGroup.findViewById(R.id.lblAerolinea);
        smartImage = viewGroup.findViewById(R.id.productImage);

        btnComprar = viewGroup.findViewById(R.id.btnComprar);


        String imageURL = "http://"+ Purchase.Url+"/WSAirline/images/"+desImage.get(i).toString()+".jpg";
        Rect rect = new Rect(smartImage.getLeft(),smartImage.getTop(),smartImage.getRight(),smartImage.getBottom());

        //Colocamos Valores
        smartImage.setImageUrl(imageURL, rect);
        txtCodigo.setText("Codigo: "+desId.get(i).toString());
        txtDestino.setText("Destino: "+destination.get(i).toString());
        txtFecha.setText("Fecha: "+desDate.get(i).toString());
        txtPrecio.setText("Precio: "+desPrice.get(i).toString());
        txtTipo.setText("Tipo: "+desTipo.get(i).toString());
        txtAerolinea.setText("Aerolinea: "+desAirline.get(i).toString());

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PurchaseActivity.class);
                intent.putExtra("IdCliente",IdUser);
                intent.putExtra("IdDestino",desId.get(i).toString());
                intent.putExtra("Destino", destination.get(i).toString());
                intent.putExtra("Fecha", desDate.get(i).toString());
                intent.putExtra("Price",desPrice.get(i).toString());
                intent.putExtra("Tipo",desTipo.get(i).toString());
                intent.putExtra("Airline",desAirline.get(i).toString());
                intent.putExtra("Image",desImage.get(i).toString());
                context.startActivity(intent);
            }
        });
        return  viewGroup;
    }
}
