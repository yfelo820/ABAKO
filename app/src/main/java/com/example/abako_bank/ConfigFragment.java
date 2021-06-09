package com.example.abako_bank;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.dialogs.ConfirmationDialog;
import com.example.abako_bank.models.Dispositivo;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;
import com.example.abako_bank.viewmodels.NotificationViewModel;
import com.example.abako_bank.viewmodels.NotificationViewModelFactory;
import com.google.android.material.tabs.TabLayout;

public class ConfigFragment extends Fragment {

    private ConfigViewModel mViewModel;

    private Switch aSwitch;

    private LinearLayout layout_pago;

    private LinearLayout layout_pago_2;

    private LinearLayout layout_pagos_limitados;

    private LinearLayout layout_dia;

    private LinearLayout layout_noche;

    private LinearLayout layout_limitados;

    private LinearLayout layout_letra;

    private LinearLayout layout_color;

    private LinearLayout layout_dias_inactividad;


    private LinearLayout user_pass;

    private MenuItem progress;

    private MenuItem enviar;


    private static final int BOTE = 1;

    private static final int CHARADA = 2;

    private int tipo_juego = BOTE; //1-bote 2-charada

    private TabLayout tabLayout;

    String device = null;
    String imei =  null;
    String profile = null;

    public static ConfigFragment newInstance() {
        return new ConfigFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Banco");
                NavHostFragment.findNavController(ConfigFragment.this)
                        .navigate(R.id.action_configFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.config_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().supportInvalidateOptionsMenu();


        tabLayout = view.findViewById(R.id.tab_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    tipo_juego = BOTE;
                    layout_dia.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    layout_limitados.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    layout_noche.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    layout_pago.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    layout_pago_2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    layout_pagos_limitados.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                if(tab.getPosition() == 1){
                    tipo_juego = CHARADA;
                    layout_dia.setBackgroundColor(getResources().getColor(R.color.other));
                    layout_limitados.setBackgroundColor(getResources().getColor(R.color.other));
                    layout_noche.setBackgroundColor(getResources().getColor(R.color.other));
                    layout_pago.setBackgroundColor(getResources().getColor(R.color.other));
                    layout_pago_2.setBackgroundColor(getResources().getColor(R.color.other));
                    layout_pagos_limitados.setBackgroundColor(getResources().getColor(R.color.other));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewModel = ViewModelProviders.of(getActivity(), new ConfigViewModelFactory())
                .get(ConfigViewModel.class);

        mViewModel.getListeroResult().observe(getViewLifecycleOwner(), new Observer<DefaultResponse>() {
            @Override
            public void onChanged(DefaultResponse defaultResponse) {
                if(defaultResponse.getStatus().equals("400")){

                    progress.setVisible(false);
                    enviar.setVisible(true);
                    Toast.makeText(getContext(),defaultResponse.getMessage(), Toast.LENGTH_LONG).show();
                }else if(defaultResponse.getStatus().equals("200")){
                    progress.setVisible(false);
                    enviar.setVisible(true);
                    Toast.makeText(getContext(),defaultResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        mViewModel.getColectorResult().observe(getViewLifecycleOwner(), new Observer<DefaultResponse>() {
            @Override
            public void onChanged(DefaultResponse defaultResponse) {
                if(defaultResponse.getStatus().equals("400")){
                    progress.setVisible(false);
                    enviar.setVisible(true);
                    Toast.makeText(getContext(),defaultResponse.getMessage(), Toast.LENGTH_LONG).show();
                }else if(defaultResponse.getStatus().equals("200")){
                    progress.setVisible(false);
                    enviar.setVisible(true);
                    Toast.makeText(getContext(),defaultResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        layout_pago = view.findViewById(R.id.layout_pago);
        layout_pago_2 = view.findViewById(R.id.layout_pago_2);
        layout_pagos_limitados = view.findViewById(R.id.layout_pagos_limitados);
        layout_dia = view.findViewById(R.id.layout_dia);
        layout_noche = view.findViewById(R.id.layout_noche);
        layout_limitados = view.findViewById(R.id.layout_limitados);
        layout_letra = view.findViewById(R.id.letra);
        user_pass = view.findViewById(R.id.user_pass);
        layout_dias_inactividad = view.findViewById(R.id.dias_inactividad);

        layout_color = view.findViewById(R.id.layout_color);




        if(getArguments() != null){
            device = getArguments().getString("device");
            imei = getArguments().getString("imei");
            profile = getArguments().getString("profile");

            if(profile.equals("Colector")){
                layout_letra.setVisibility(View.VISIBLE);
                layout_dias_inactividad.setVisibility(View.VISIBLE);
                user_pass.setVisibility(View.VISIBLE);
                layout_pago.setVisibility(View.GONE);
                layout_pago_2.setVisibility(View.GONE);
                layout_pagos_limitados.setVisibility(View.GONE);
                layout_dia.setVisibility(View.GONE);
                layout_noche.setVisibility(View.GONE);
                layout_limitados.setVisibility(View.GONE);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Config Colector");
                tabLayout.setVisibility(View.GONE);
            }else if(profile.equals("Listero")){
                layout_letra.setVisibility(View.VISIBLE);
                layout_dias_inactividad.setVisibility(View.GONE);
                user_pass.setVisibility(View.VISIBLE);
                layout_pago.setVisibility(View.VISIBLE);
                layout_pago_2.setVisibility(View.VISIBLE);
                layout_pagos_limitados.setVisibility(View.VISIBLE);
                layout_dia.setVisibility(View.VISIBLE);
                layout_noche.setVisibility(View.VISIBLE);
                layout_limitados.setVisibility(View.VISIBLE);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Config Listero");
                tabLayout.setVisibility(View.VISIBLE);
            }

            final Bundle bundle = new Bundle();
            bundle.putString("device",device);
            bundle.putInt("tipo_juego",tipo_juego);
            bundle.putString("imei",imei);
            System.out.println("Viendo"+profile);
            bundle.putString("profile", profile);



            /*if(tipo_juego == 1){
                tipo_juego_text.setText("Tipo de juego: Bote");

                layout_color.setBackgroundColor(Color.parseColor("#0099cb"));
            }else if(tipo_juego == 2){
                tipo_juego_text.setText("Tipo de juego: Charada");

                layout_color.setBackgroundColor(Color.parseColor("#cc0001"));
            }*/

            /*if(profile.equals("Colector")){
                switch1.setVisibility(View.GONE);
            }else if(profile.equals("Listero")){
                switch1.setVisibility(View.VISIBLE);
            }*/


            layout_pago.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(ConfigFragment.this)
                            .navigate(R.id.action_configFragment_to_configPagosFragment, bundle);
                }
            });


            layout_pago_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(ConfigFragment.this)
                            .navigate(R.id.action_configFragment_to_configPagosFragment2, bundle);
                }
            });


            layout_pagos_limitados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(ConfigFragment.this)
                            .navigate(R.id.action_configFragment_to_configPagosLimFragment, bundle);
                }
            });


            layout_dia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(ConfigFragment.this)
                            .navigate(R.id.action_configFragment_to_configHDiaFragment, bundle);
                }
            });


            layout_noche.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(ConfigFragment.this)
                            .navigate(R.id.action_configFragment_to_configHNocheFragment, bundle);
                }
            });


            layout_limitados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(ConfigFragment.this)
                            .navigate(R.id.action_configFragment_to_configLimFragment, bundle);
                }
            });


            layout_letra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(profile.equals("Colector")) {
                        NavHostFragment.findNavController(ConfigFragment.this)
                                .navigate(R.id.action_configFragment_to_configLetraFragment, bundle);
                    }else if(profile.equals("Listero")){
                        NavHostFragment.findNavController(ConfigFragment.this)
                                .navigate(R.id.action_configFragment_to_configLetraListeroFragment, bundle);
                    }

                }
            });


            user_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(ConfigFragment.this)
                            .navigate(R.id.action_configFragment_to_userPassFragment, bundle);
                }
            });


            layout_dias_inactividad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(ConfigFragment.this)
                            .navigate(R.id.action_configFragment_to_diasInactivoFragment, bundle);
                }
            });

            /*switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        //Language1 code
                        //((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cc0001")));
                        Toast.makeText(getContext(),"Cambiado a modo CHARADA", Toast.LENGTH_SHORT).show();
                        tipo_juego = 2;//charada

                        layout_color.setBackgroundColor(Color.parseColor("#cc0001"));

                        tipo_juego_text.setText("Tipo de juego: Charada");

                    }else{
                        //((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099cb")));
                        Toast.makeText(getContext(),"Cambiado a modo BOTE", Toast.LENGTH_SHORT).show();
                        tipo_juego = 1;//bote
                        layout_color.setBackgroundColor(Color.parseColor("#0099cb"));
                        tipo_juego_text.setText("Tipo de juego: Bote");
                    }
                }
            });*/
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.configuration_menu, menu);
        /*View sw = menu.findItem(R.id.app_bar_switch).getActionView();
        aSwitch = (Switch) sw.findViewById(R.id.my_menu_switch);*/

        progress = menu.findItem(R.id.app_bar_loading);

        enviar = menu.findItem(R.id.enviar_config);


        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onResume() {
        getActivity().invalidateOptionsMenu();
        super.onResume();

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {

        progress = menu.findItem(R.id.app_bar_loading);

        enviar = menu.findItem(R.id.enviar_config);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.enviar_config){

            progress.setVisible(true);
            enviar.setVisible(false);

            if(profile.equals("Colector")){
                Dispositivo dispositivo = new Dispositivo();
                dispositivo.setImei(imei);
                dispositivo.setUid_dispositivo(device);
                mViewModel.addConfiguracionColector(dispositivo);
            }else{//configurar Listero
                Dispositivo dispositivo = new Dispositivo();
                dispositivo.setImei(imei);
                dispositivo.setUid_dispositivo(device);
                mViewModel.addConfiguracionListero(dispositivo);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}