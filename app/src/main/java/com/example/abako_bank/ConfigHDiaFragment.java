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
import android.widget.TextView;
import android.widget.Toast;

import com.example.abako_bank.dialogs.AgregarDispDialog;
import com.example.abako_bank.dialogs.ConfirmationDialog;
import com.example.abako_bank.models.config.Config_Horario_Dia;
import com.example.abako_bank.models.config.Config_Limitados;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigHDiaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigHDiaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "device";
    private static final String ARG_PARAM2 = "tipo_juego";
    private static final String ARG_PARAM3 = "imei";

    private ConfigViewModel configViewModel;

    // TODO: Rename and change types of parameters
    private String device;
    private int tipo_juego;
    private String imei;


    private EditText inactivo_dia_hora;

    private EditText inactivo_dia_minuto;

    private EditText activo_dia_hora;

    private EditText activo_dia_minuto;



    public ConfigHDiaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigHDiaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigHDiaFragment newInstance(String param1, String param2, String param3) {
        ConfigHDiaFragment fragment = new ConfigHDiaFragment();
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
                ConfirmationDialog selectProfileDialog = ConfirmationDialog.newInstance(ConfigHDiaFragment.this, device, imei, "");
                selectProfileDialog.show(fm, "fragment_confirmation");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_config_h_dia, container, false);
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

        boolean formato = true;

        if(id == R.id.salvar){
            if(inactivo_dia_hora.getText().length() == 0 || inactivo_dia_hora.getText().toString().isEmpty()){
                inactivo_dia_hora.setError("El campo es obligatorio");
            }else if(Integer.valueOf(inactivo_dia_hora.getText().toString()) < 0 || Integer.valueOf(inactivo_dia_hora.getText().toString()) > 24){
                inactivo_dia_hora.setError("Error de formato en la hora");
                formato = false;
            }
            if(inactivo_dia_minuto.getText().length() == 0 || inactivo_dia_minuto.getText().toString().isEmpty()){
                inactivo_dia_minuto.setError("El campo es obligatorio");
            }else  if(Integer.valueOf(inactivo_dia_minuto.getText().toString()) < 0 || Integer.valueOf(inactivo_dia_minuto.getText().toString()) > 60){
                inactivo_dia_minuto.setError("Error de formato en los minutos");
                formato = false;
            }
            if(activo_dia_hora.getText().length() == 0 || activo_dia_hora.getText().toString().isEmpty()){
                activo_dia_hora.setError("El campo es obligatorio");
            }else if(Integer.valueOf(activo_dia_hora.getText().toString()) < 0 || Integer.valueOf(activo_dia_hora.getText().toString()) > 24){
                activo_dia_hora.setError("Error de formato en la hora");
                formato = false;
            }
            if(activo_dia_minuto.getText().length() == 0 || activo_dia_minuto.getText().toString().isEmpty()){
                activo_dia_minuto.setError("El campo es obligatorio");
            }else if(Integer.valueOf(activo_dia_minuto.getText().toString()) < 0 || Integer.valueOf(activo_dia_minuto.getText().toString()) > 60){
                activo_dia_minuto.setError("Erro de formato en los minutos");
                formato = false;
            }

            if(inactivo_dia_hora.getText().length() != 0 && !inactivo_dia_hora.getText().toString().isEmpty() && inactivo_dia_minuto.getText().length() != 0 && !inactivo_dia_minuto.getText().toString().isEmpty()
                    && activo_dia_hora.getText().length() != 0 && !activo_dia_hora.getText().toString().isEmpty() && activo_dia_minuto.getText().length() != 0 && !activo_dia_minuto.getText().toString().isEmpty()
            && formato == true) {

                configViewModel.saveConfigHorarioDia(tipo_juego, inactivo_dia_hora.getText().toString(), inactivo_dia_minuto.getText().toString(),
                        activo_dia_hora.getText().toString(), activo_dia_minuto.getText().toString());

                Toast.makeText(getContext(), "Configuración salvada correctamente", Toast.LENGTH_LONG).show();
                Bundle args = new Bundle();

                args.putString("device", device);
                args.putString("imei", imei);
                args.putString("profile", "Listero");

                NavHostFragment.findNavController(ConfigHDiaFragment.this)
                        .navigate(R.id.action_configHDiaFragment_to_configFragment, args);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configViewModel = ViewModelProviders.of(this, new ConfigViewModelFactory())
                .get(ConfigViewModel.class);

        inactivo_dia_hora = view.findViewById(R.id.hora_dia_inactivo);

        inactivo_dia_minuto = view.findViewById(R.id.minuto_dia_inactivo);

        activo_dia_hora = view.findViewById(R.id.hora_dia_activo);

        activo_dia_minuto = view.findViewById(R.id.minuto_dia_activo);

        List<Config_Horario_Dia> config_horario_dias = Config_Horario_Dia.find(Config_Horario_Dia.class, "tipojuego = ?", String.valueOf(tipo_juego));
        if(!config_horario_dias.isEmpty()){

            inactivo_dia_hora.setText(config_horario_dias.get(0).getHora_inactivo());
            inactivo_dia_minuto.setText(config_horario_dias.get(0).getMinuto_inactivo());
            activo_dia_hora.setText(String.valueOf(config_horario_dias.get(0).getHora_activo()));
            activo_dia_minuto.setText(String.valueOf(config_horario_dias.get(0).getMinuto_activo()));
        }
    }
}