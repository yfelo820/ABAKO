package com.example.abako_bank;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
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
import com.example.abako_bank.models.config.Config_Percent_Pago;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigPorcentPagosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigPorcentPagosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "device";
    private static final String ARG_PARAM2 = "tipo_juego";
    private static final String ARG_PARAM3 = "imei";

    private EditText pago_normal_bola;

    private EditText pago_normal_parlet;

    private EditText pago_normal_centena;

    private EditText pago_no_normal_bola;

    private EditText pago_no_normal_parlet;

    private EditText pago_no_normal_centena;

    private ConfigViewModel mViewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String device;
    private int tipo_juego;
    private String imei;

    public ConfigPorcentPagosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigPagosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigPorcentPagosFragment newInstance(String param1, String param2, String param3) {
        ConfigPorcentPagosFragment fragment = new ConfigPorcentPagosFragment();
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
            device = getArguments().getString(ARG_PARAM1);
            tipo_juego = getArguments().getInt(ARG_PARAM2);
            imei = getArguments().getString(ARG_PARAM3);
        }
        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ConfirmationDialog selectProfileDialog = ConfirmationDialog.newInstance(ConfigPorcentPagosFragment.this, device, imei,"");
                selectProfileDialog.show(fm, "fragment_confirmation");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_config_porcent_pagos, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.salvar_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = ViewModelProviders.of(this, new ConfigViewModelFactory())
                .get(ConfigViewModel.class);

        pago_normal_bola = view.findViewById(R.id.pago_normal_bola);

        pago_normal_centena = view.findViewById(R.id.pago_normal_centena);

        pago_normal_parlet = view.findViewById(R.id.pago_normal_parlet);

        pago_no_normal_bola = view.findViewById(R.id.pago_no_normal_bola);

        pago_no_normal_parlet = view.findViewById(R.id.pago_no_normal_parlet);

        pago_no_normal_centena = view.findViewById(R.id.pago_no_normal_centena);


        if(getArguments() != null){
            device = getArguments().getString("device");
            tipo_juego = getArguments().getInt("tipo_juego");

            List<Config_Percent_Pago> config_percent_pagos = Config_Percent_Pago.find(Config_Percent_Pago.class,"tipojuego = ?", String.valueOf(tipo_juego));
            if(!config_percent_pagos.isEmpty()){
                Config_Percent_Pago config_percent_pago = config_percent_pagos.get(0);
                pago_normal_centena.setText(String.valueOf(config_percent_pago.getCentena_normal()));
                pago_normal_parlet.setText(String.valueOf(config_percent_pago.getParlet_normal()));
                pago_normal_bola.setText(String.valueOf(config_percent_pago.getBola_normal()));

                pago_no_normal_centena.setText(String.valueOf(config_percent_pago.getCentene_no_normal()));
                pago_no_normal_parlet.setText(String.valueOf(config_percent_pago.getParlet_no_normal()));
                pago_no_normal_bola.setText(String.valueOf(config_percent_pago.getBola_no_normal()));
            }else{
                mViewModel.saveConfigPagosPercent(tipo_juego, "100", "100",
                        "100", "100",
                        "100", "100");
            }

        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.salvar){
            if(pago_normal_bola.getText().length() == 0 || pago_normal_bola.getText().toString().isEmpty()){
                pago_normal_bola.setError("El campo es obligatorio");
            }

            if(pago_normal_centena.getText().length() == 0 || pago_normal_centena.getText().toString().isEmpty()){
                pago_normal_centena.setError("El campo es obligatorio");
            }

            if(pago_normal_parlet.getText().length() == 0 || pago_normal_parlet.getText().toString().isEmpty()){
                pago_normal_parlet.setError("El campo es obligatorio");
            }

            if(pago_no_normal_bola.getText().length() == 0 || pago_no_normal_bola.getText().toString().isEmpty()){
                pago_no_normal_bola.setError("El campo es obligatorio");
            }

            if(pago_no_normal_parlet.getText().length() == 0 || pago_no_normal_parlet.getText().toString().isEmpty()){
                pago_no_normal_parlet.setError("El campo es obligatorio");
            }

            if(pago_no_normal_centena.getText().length() == 0 || pago_no_normal_centena.getText().toString().isEmpty()){
                pago_no_normal_centena.setError("El campo es obligatorio");
            }

            if(pago_normal_bola.getText().length() != 0 && !pago_normal_bola.getText().toString().isEmpty() && pago_normal_centena.getText().length() != 0 && !pago_normal_centena.getText().toString().isEmpty()
                    && pago_normal_parlet.getText().length() != 0 && !pago_normal_parlet.getText().toString().isEmpty() && pago_no_normal_bola.getText().length() != 0 && !pago_no_normal_bola.getText().toString().isEmpty()
                    && pago_no_normal_parlet.getText().length() != 0 && !pago_no_normal_parlet.getText().toString().isEmpty()
                    && pago_no_normal_centena.getText().length() != 0 && !pago_no_normal_centena.getText().toString().isEmpty()){

                    mViewModel.saveConfigPagosPercent(tipo_juego, pago_normal_bola.getText().toString(), pago_no_normal_bola.getText().toString(),
                            pago_normal_parlet.getText().toString(), pago_no_normal_parlet.getText().toString(),
                            pago_normal_centena.getText().toString(), pago_no_normal_centena.getText().toString());

                    Toast.makeText(getContext(), "Configuración salvada correctamente", Toast.LENGTH_LONG).show();

                    Bundle args = new Bundle();

                    args.putString("device", device);
                    args.putString("imei", imei);
                    args.putString("profile", "Listero");

                    NavHostFragment.findNavController(ConfigPorcentPagosFragment.this)
                            .navigate(R.id.action_configPagosFragment_to_configFragment, args);



            }
        }

        return super.onOptionsItemSelected(item);
    }


}