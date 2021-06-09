package com.example.abako_bank;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.BankUser;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.NotificationResponse;
import com.example.abako_bank.api.response.SocketResponse;
import com.example.abako_bank.api.response.prizes.PrizesListasResponse;
import com.example.abako_bank.dialogs.LoginDialogFragment;
import com.example.abako_bank.models.Settings;
import com.example.abako_bank.models.models2.AdapterDatos;
import com.example.abako_bank.models.models2.Bola;
import com.example.abako_bank.models.models2.Centena;
import com.example.abako_bank.models.models2.GetPlaysAdapter;
import com.example.abako_bank.models.models2.Lista1;
import com.example.abako_bank.models.models2.Parlet;
import com.example.abako_bank.models.models2.Play;
import com.example.abako_bank.models.models2.PlaysResponse;
import com.example.abako_bank.viewmodels.ListasViewModel;
import com.example.abako_bank.viewmodels.ListasViewModelFactory;
import com.example.abako_bank.viewmodels.LoginViewModel;
import com.example.abako_bank.viewmodels.LoginViewModelFactory;
import com.example.abako_bank.websocket.App;
import com.example.abako_bank.websocket.models.SocketEventModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JugadasLista extends Fragment {

    ArrayList<Lista1> listDatos  = new ArrayList<>();
    ArrayList<Lista1> listDatosDia  = new ArrayList<>();
    ArrayList<Lista1> listDatosNoche  = new ArrayList<>();

    ListView txtContenido;
    int id_lista_tocada = 0, total_pages = 0, current_page = 0;
    double bruto = 0, limpio = 0;
    ArrayList<String> bola_by_id = new ArrayList<>();
    ArrayList<String> centena_by_id = new ArrayList<>();
    ArrayList<String> parlet_by_id = new ArrayList<>();
    List<Play> jugadas = new ArrayList<>();

    boolean dia = true;
    int page_paginado = 0;

    TextView fecha, AMPM;
    Button btnSgt, btnAnt;

    public JugadasLista() {
        /* Required empty public constructor
        id_lista_tocada = 0;
        total_pages = 0;
        current_page = 0;
        bruto = 0;
        limpio = 0;
        bola_by_id = new ArrayList<String>();
        centena_by_id = new ArrayList<String>();
        parlet_by_id = new ArrayList<String>();
        listDatos = new ArrayList<Lista1>();
        listDatosDia = new ArrayList<Lista1>();
        listDatosNoche = new ArrayList<Lista1>();
        jugadas = new ArrayList<Play>();  */

    }

    // TODO: Rename and change types and number of parameters
    public static JugadasLista newInstance(String param1, String param2) {
        JugadasLista fragment = new JugadasLista();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.jugadas_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fecha = (TextView) view.findViewById(R.id.txtFecha);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        fecha.setText(formattedDate);

        AMPM = (TextView) view.findViewById(R.id.txtAMPM);
        btnSgt = (Button) view.findViewById(R.id.btnSgt);
        btnAnt = (Button) view.findViewById(R.id.btnAnt);

        txtContenido = (ListView) view.findViewById(R.id.txtContenido);

        CargarJugadas(0);
        //Log.d("Mensaje BIEN msg: ", String.valueOf(listDatos.get(0).id));

        AMPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AMPM.getText()=="AM") {
                    AMPM.setText("PM");
                    page_paginado = 0;
                    dia = false;
                    Paginar(false, page_paginado, listDatosNoche);

                }
                else {
                    AMPM.setText("AM");
                    ArrayAdapter<Lista1> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listDatosDia);
                    txtContenido.setAdapter(adapter);
                    page_paginado = 0;
                    dia = true;
                    Paginar(true, page_paginado, listDatosNoche);
                }

            }
        });

        btnSgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( page_paginado < total_pages-1 && dia ){

                    Paginar(true, page_paginado+1, listDatosDia);
                    page_paginado++;
                }
                else if( page_paginado < total_pages-1 && !dia ){

                    Paginar(true, page_paginado+1, listDatosNoche);
                    page_paginado++;
                }
                else Toast.makeText(getContext(), "ULTIMA PÁGINA", Toast.LENGTH_SHORT).show();
            }
        });

        btnAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( page_paginado > 0 && dia ){

                    Paginar(true, page_paginado-1, listDatosDia);
                    page_paginado--;
                }
                else if( page_paginado > 0 && !dia ){

                    Paginar(true, page_paginado-1, listDatosNoche);
                    page_paginado--;
                }
                else Toast.makeText(getContext(), "PRIMERA PÁGINA", Toast.LENGTH_SHORT).show();

            }
        });


        txtContenido.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Toast.makeText(getContext(),"DOUBLE TAP",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent (getContext(), JugadasDetalles.class);
                    Bundle myBundle = new Bundle();
                    myBundle.putStringArrayList("bolas",bola_by_id);
                    myBundle.putStringArrayList("centenas",centena_by_id);
                    myBundle.putStringArrayList("parlets",parlet_by_id);
                    myBundle.putString("limpio",String.valueOf(limpio));
                    myBundle.putString("bruto",String.valueOf(bruto));
                    intent.putExtras(myBundle);
                    startActivityForResult(intent, 0);
                    return super.onDoubleTap(e);
                }
            });


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });

        txtContenido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Lista1 l1 = listDatos.get(i);
                id_lista_tocada = l1.id;
                Toast.makeText(getContext(), String.valueOf(l1.id),Toast.LENGTH_LONG).show();

                List<Bola> bolaList = new ArrayList<Bola>();
                List<Centena> centenaList = new ArrayList<Centena>();
                List<Parlet> parletList = new ArrayList<Parlet>();

                for (Play p: jugadas )
                    if( p._id == id_lista_tocada ) {
                        bolaList = p.getBolas();
                        centenaList = p.getCentenas();
                        parletList = p.getParlets();
                        bruto = p.getBruto();
                        limpio = p.getLimpio();
                    }

                for(int j=0;j<bolaList.size();j++)
                    bola_by_id.add(bolaList.get(j).numero+"  |  "+bolaList.get(j).monto_corrido+"  |  "+bolaList.get(j).monto_fijo);

                for(int j=0;j<centenaList.size();j++)
                    centena_by_id.add(centenaList.get(j).numero+"  |  "+centenaList.get(j).monto+"  |  "+centenaList.get(j).premio);

                for(int j=0;j<parletList.size();j++)
                    parlet_by_id.add(parletList.get(j).numero+"  |  "+parletList.get(j).monto+"  |  "+parletList.get(j).monto_entregado);
            }
        });
    }

    public  void CargarJugadas(int page)
    {
        //final com.example.abako_bank.models.Settings settings = com.example.abako_bank.models.Settings.listAll(Settings.class).get(0);
        //String token1 = settings.getToken();
        String token1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaGVjayI6dHJ1ZSwiaWF0IjoxNjIyNjQyOTk5LCJleHAiOjE2MjI2Nzg5OTl9.TVkdZWCINZv0Bjdg88ZYMVnpdYNPHMYxK6a58hHnNCI";
        String device = "123456789";
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        //final PlaysResponse[] response1 = {new PlaysResponse()};
        final Call<PlaysResponse> call = GetPlaysAdapter.getApiService().GetPlays(token1, device,"2021-06-01", page , 25);
        //responseCall.enqueue((Callback<PlaysResponse>) this);

        call.enqueue(new Callback<PlaysResponse>() {
            @Override public void onResponse(Call<PlaysResponse> call, Response<PlaysResponse> response)
            {
                if(response.isSuccessful()) {
                    PlaysResponse respon = response.body();
                    Log.d("Mensaje BIEN id: ", String.valueOf(response.body().plays.get(0)._id));
                    Log.d("Mensaje BIEN cp: ", String.valueOf(response.body().currentPage));
                    Log.d("Mensaje BIEN ti: ", String.valueOf(response.body().totalItems));
                    Log.d("Mensaje BIEN tp: ", String.valueOf(response.body().totalPages));
                    Log.d("Mensaje BIEN msg: ", String.valueOf(response.body().message));

                    total_pages = respon.totalPages;
                    current_page = respon.currentPage;

                    if(total_pages > 1) {
                        int cant = respon.getPlays().size();
                        for(int i=0;i<cant;i++){
                            jugadas.add(respon.getPlays().get(i));
                        }
                    } else jugadas = respon.getPlays();

                    for (Play p : respon.plays) {
                        listDatos.add(new Lista1(p._id, p.listero.nombre, p.limpio, p.bruto, p.ganancia_banco));
                        if (p.tipo_tirada == 1) listDatosDia.add(new Lista1(p._id, p.listero.nombre, p.limpio, p.bruto, p.ganancia_banco));
                        else listDatosNoche.add(new Lista1(p._id, p.listero.nombre, p.limpio, p.bruto, p.ganancia_banco));
                    }

                    if (total_pages == 1) {
                        ArrayAdapter<Lista1> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listDatosDia);
                        txtContenido.setAdapter(adapter);
                        //EnviarListaJugadas(listDatos);
                    }   else {
                        current_page++;
                        if (current_page < total_pages)
                            CargarJugadas(current_page);
                        if (current_page == total_pages)
                            Paginar(true, 0, listDatosDia);
                    }

                    Log.d("Mensaje BIEN Datos msg: ", String.valueOf(listDatos.size()));
                    Log.d("Mensaje BIEN Jugadas msg: ", String.valueOf(jugadas.size()));

                }
                // have your all data int id =response.body().getId(); String userName = response.body().getUsername(); String level = response.body().getLevel();
            }
            @Override public void onFailure(Call<PlaysResponse> call, Throwable t)
            {
                Log.d("Mensaje ERROR: ", "Estoy Fallando "+t.getMessage());
            }
        });


    }  //Fin Metodo Cargar Jugadas

    public void Paginar(boolean dia, int page ,List<Lista1> lista) {

        List<Lista1> listaDiaNoche = new ArrayList<>();

        if(dia){

            for(int i=page*25;i<(page+1)*25;i++)
                if(lista.size() > i)
                listaDiaNoche.add(lista.get(i));

            ArrayAdapter<Lista1> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listaDiaNoche);
            txtContenido.setAdapter(adapter);

        } else {

            for(int i=page*25;i<(page+1)*25;i++)
                if(lista.size() > i)
                listaDiaNoche.add(lista.get(i));

            ArrayAdapter<Lista1> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listaDiaNoche);
            txtContenido.setAdapter(adapter);
        }


    }  // Fin de Metodo Paginar
}