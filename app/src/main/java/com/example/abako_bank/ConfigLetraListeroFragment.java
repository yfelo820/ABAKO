package com.example.abako_bank;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abako_bank.adapters.SpinnerStringAdapter;
import com.example.abako_bank.api.response.ColectorsResponse;
import com.example.abako_bank.dialogs.ConfirmationDialog;
import com.example.abako_bank.models.Colector;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigLetraListeroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigLetraListeroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "device";
    private static final String ARG_PARAM2 = "tipo_juego";
    private static final String ARG_PARAM3 = "imei";

    // TODO: Rename and change types of parameters
    private String device;
    private int tipo_juego;
    private String imei;

    private ConfigViewModel configViewModel;

    private Spinner spinner;

    private TextView letra;

    private List<Colector> colectors;

    private Colector colector_selected;

    public ConfigLetraListeroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LetraListeroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigLetraListeroFragment newInstance(String param1, String param2, String param3) {
        ConfigLetraListeroFragment fragment = new ConfigLetraListeroFragment();
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
                ConfirmationDialog selectProfileDialog = ConfirmationDialog.newInstance(ConfigLetraListeroFragment.this, device, imei,"");
                selectProfileDialog.show(fm, "fragment_confirmation");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_config_letra_listero, container, false);
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
            if(letra.getText().length() != 0 && !letra.getText().toString().isEmpty()) {

                configViewModel.saveLetraListero(letra.getText().toString(), device,colector_selected.getNombre());
                Toast.makeText(getContext(), "Configuración salvada correctamente", Toast.LENGTH_LONG).show();

                Bundle args = new Bundle();

                args.putString("device", device);
                args.putString("imei", imei);
                args.putString("profile", "Listero");

                NavHostFragment.findNavController(ConfigLetraListeroFragment.this)
                        .navigate(R.id.action_configLetraListeroFragment_to_configFragment, args);


            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configViewModel = ViewModelProviders.of(getActivity(), new ConfigViewModelFactory())
                .get(ConfigViewModel.class);

        colectors = new ArrayList<>();

        letra = view.findViewById(R.id.letra);

        configViewModel.getAllColectores();

        spinner = (Spinner) view.findViewById(R.id.spinner);

        configViewModel.getColectoresTodos().observe(getViewLifecycleOwner(), new Observer<ColectorsResponse>() {
            @Override
            public void onChanged(ColectorsResponse colectorsResponse) {
                colectors = colectorsResponse.getCollectors();

                // Create an ArrayAdapter using the string array and a default spinner layout
                SpinnerStringAdapter adapter = new SpinnerStringAdapter(getActivity(), android.R.layout.simple_spinner_item,colectorsResponse.getCollectors());
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                colector_selected = colectors.get(i);

                if(colector_selected.getListeros() != null){
                    letra.setText(colector_selected.getNombre()+String.valueOf(colector_selected.getListeros().size()+1));
                }else{
                    letra.setText(colector_selected.getNombre()+"1");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
}