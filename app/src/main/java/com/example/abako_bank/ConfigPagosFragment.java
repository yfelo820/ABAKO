package com.example.abako_bank;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abako_bank.dialogs.ConfirmationDialog;
import com.example.abako_bank.models.config.Config_Pagos;
import com.example.abako_bank.models.config.Config_Percent_Pago;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;

import java.util.List;

public class ConfigPagosFragment extends Fragment {

    private ConfigViewModel mViewModel;
    String device = null;
    Integer tipo_juego = 2;
    String imei = null;

    private static final String ARG_PARAM1 = "device";
    private static final String ARG_PARAM2 = "tipo_juego";
    private static final String ARG_PARAM3 = "imei";

    private EditText pago_fijo;
    private EditText pago_corrido;
    private EditText pago_parlet;
    private EditText pago_parlet_m;
    private EditText pago_centena;

    public static ConfigPagosFragment newInstance(String param1, String param2, String param3) {
        ConfigPagosFragment fragment = new ConfigPagosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            device = getArguments().getString("device");
            tipo_juego = getArguments().getInt("tipo_juego");
            imei = getArguments().getString("imei");
        }
        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ConfirmationDialog selectProfileDialog = ConfirmationDialog.newInstance(ConfigPagosFragment.this, device, imei,"");
                selectProfileDialog.show(fm, "fragment_confirmation");
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.config_pagos_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = ViewModelProviders.of(this, new ConfigViewModelFactory())
                .get(ConfigViewModel.class);

        pago_centena = view.findViewById(R.id.pago_centena);

        pago_corrido = view.findViewById(R.id.pago_corrido);

        pago_fijo = view.findViewById(R.id.pago_fijo);

        pago_parlet = view.findViewById(R.id.pago_parlet);

        pago_parlet_m = view.findViewById(R.id.pago_parlet_m);


        if(getArguments() != null){
            device = getArguments().getString("device");
            tipo_juego = getArguments().getInt("tipo_juego");
            imei = getArguments().getString("imei");

            List<Config_Pagos> config_percent_pagos = Config_Pagos.find(Config_Pagos.class,"tipojuego = ?", String.valueOf(tipo_juego));
            if(!config_percent_pagos.isEmpty()){
                Config_Pagos config_percent_pago = config_percent_pagos.get(0);
                pago_centena.setText(String.valueOf(config_percent_pago.getCentena()));
                pago_corrido.setText(String.valueOf(config_percent_pago.getCorrido()));
                pago_fijo.setText(String.valueOf(config_percent_pago.getFijo()));

                pago_parlet.setText(String.valueOf(config_percent_pago.getParlet()));
                pago_parlet_m.setText(String.valueOf(config_percent_pago.getParlet_m()));
            }else{
                mViewModel.saveConfigPagos(tipo_juego, "100", "100",
                        "100", "100", "100");
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.salvar_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.salvar){
            if(pago_parlet_m.getText().length() == 0 || pago_parlet_m.getText().toString().isEmpty()){
                pago_parlet_m.setError("El campo es obligatorio");
            }

            if(pago_parlet.getText().length() == 0 || pago_parlet.getText().toString().isEmpty()){
                pago_parlet.setError("El campo es obligatorio");
            }

            if(pago_fijo.getText().length() == 0 || pago_fijo.getText().toString().isEmpty()){
                pago_fijo.setError("El campo es obligatorio");
            }

            if(pago_corrido.getText().length() == 0 || pago_corrido.getText().toString().isEmpty()){
                pago_corrido.setError("El campo es obligatorio");
            }

            if(pago_centena.getText().length() == 0 || pago_centena.getText().toString().isEmpty()){
                pago_centena.setError("El campo es obligatorio");
            }

            if(pago_centena.getText().length() != 0 && !pago_centena.getText().toString().isEmpty() && pago_corrido.getText().length() != 0 && !pago_corrido.getText().toString().isEmpty()
                    && pago_fijo.getText().length() != 0 && !pago_fijo.getText().toString().isEmpty() && pago_parlet.getText().length() != 0 && !pago_parlet.getText().toString().isEmpty()
                    && pago_parlet_m.getText().length() != 0 && !pago_parlet_m.getText().toString().isEmpty()) {

                mViewModel.saveConfigPagos(tipo_juego, pago_centena.getText().toString(), pago_corrido.getText().toString(),
                        pago_fijo.getText().toString(), pago_parlet.getText().toString(), pago_parlet_m.getText().toString());

                Toast.makeText(getContext(), "Configuración salvada correctamente", Toast.LENGTH_LONG).show();

                Bundle args = new Bundle();

                args.putString("device", device);
                args.putString("imei", imei);
                args.putString("profile", "Listero");

                NavHostFragment.findNavController(ConfigPagosFragment.this)
                        .navigate(R.id.action_configPagosFragment2_to_configFragment, args);

            }
        }

        return super.onOptionsItemSelected(item);
    }

}