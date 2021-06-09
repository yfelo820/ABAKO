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
import android.widget.TextView;
import android.widget.Toast;

import com.example.abako_bank.dialogs.ConfirmationDialog;
import com.example.abako_bank.models.config.Config_Horario_Dia;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigLetraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigLetraFragment extends Fragment {

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

    private TextView letra;



    public ConfigLetraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigLetraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigLetraFragment newInstance(String param1, String param2, String param3) {
        ConfigLetraFragment fragment = new ConfigLetraFragment();
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
                ConfirmationDialog selectProfileDialog = ConfirmationDialog.newInstance(ConfigLetraFragment.this, device, imei,"");
                selectProfileDialog.show(fm, "fragment_confirmation");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_config_letra, container, false);
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
            if(letra.getText().length() == 0 || letra.getText().toString().isEmpty()){
                letra.setError("El campo es obligatorio");
            }
            Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
            Matcher ms = ps.matcher(letra.getText().toString());
            boolean bs = ms.matches();
            if (bs == false) {
                letra.setError("Solo se permiten caracteres de A-Z");

            }
            if(letra.getText().length() != 0 && !letra.getText().toString().isEmpty() && bs == true) {

                configViewModel.saveLetra(letra.getText().toString(), device);
                Toast.makeText(getContext(), "Configuración salvada correctamente", Toast.LENGTH_LONG).show();

                Bundle args = new Bundle();

                args.putString("device", device);
                args.putString("imei", imei);
                args.putString("profile", "Colector");

                NavHostFragment.findNavController(ConfigLetraFragment.this)
                        .navigate(R.id.action_configLetraFragment_to_configFragment, args);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configViewModel = ViewModelProviders.of(this, new ConfigViewModelFactory())
                .get(ConfigViewModel.class);

        letra = view.findViewById(R.id.letra);


    }
}