package com.example.abako_bank;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class JugadasDetalles extends AppCompatActivity {

    ArrayList<String> bola_by_id;
    ArrayList<String> centena_by_id;
    ArrayList<String> parlet_by_id;
    ListView txtContenido;

    Button btnParlet;
    Button btnCentena;
    Button btnBola;

    TextView limpio, bruto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jugadas_detalles);

        limpio = (TextView) findViewById(R.id.txtSaldoLimpio);
        bruto = (TextView) findViewById(R.id.txtSaldoBruto);

        txtContenido = (ListView) findViewById(R.id.txtContenido);
        btnParlet = (Button) findViewById(R.id.btnParlet);
        btnCentena = (Button) findViewById(R.id.btnCentena);
        btnBola = (Button) findViewById(R.id.btnBola);

        bola_by_id = new ArrayList<String>();
        centena_by_id = new ArrayList<String>();
        parlet_by_id = new ArrayList<String>();
        Bundle myBundle = this.getIntent().getExtras();

        if(myBundle!=null){

         bola_by_id = myBundle.getStringArrayList("bolas");
         centena_by_id = myBundle.getStringArrayList("centenas");
         parlet_by_id = myBundle.getStringArrayList("parlets");
         limpio.setText(myBundle.getString("limpio"));
         bruto.setText(myBundle.getString("bruto"));
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bola_by_id);
         txtContenido.setAdapter(adapter);
         btnBola.setBackgroundColor(Color.parseColor("#E65131"));

        }

        btnParlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ButtonParletClick();
                btnParlet.setBackgroundColor(Color.parseColor("#E65131"));
                btnBola.setBackgroundColor(Color.parseColor("#C0C0C0"));
                btnCentena.setBackgroundColor(Color.parseColor("#C0C0C0"));

            }
        });

        btnCentena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ButtonCentenaClick();
                btnCentena.setBackgroundColor(Color.parseColor("#E65131"));
                btnBola.setBackgroundColor(Color.parseColor("#C0C0C0"));
                btnParlet.setBackgroundColor(Color.parseColor("#C0C0C0"));
            }
        });

        btnBola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ButtonBolaClick();
                btnBola.setBackgroundColor(Color.parseColor("#E65131"));
                btnCentena.setBackgroundColor(Color.parseColor("#C0C0C0"));
                btnParlet.setBackgroundColor(Color.parseColor("#C0C0C0"));
            }
        });


    }

    private void ButtonBolaClick() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bola_by_id);
        txtContenido.setAdapter(adapter);
    }

    private void ButtonCentenaClick() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, centena_by_id);
        txtContenido.setAdapter(adapter);
    }

    private void ButtonParletClick() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parlet_by_id);
        txtContenido.setAdapter(adapter);

    }
}
