package com.example.abako_bank;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abako_bank.dialogs.BlackListDeviceFragment;
import com.example.abako_bank.dialogs.ConfirmationDialog;
import com.example.abako_bank.dialogs.LimitadosDialog;
import com.example.abako_bank.dialogs.NoJueganDialog;
import com.example.abako_bank.dialogs.TopeEditDialog;
import com.example.abako_bank.models.config.Config_Limitados;
import com.example.abako_bank.models.config.Config_Topes_Lim;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;
import com.example.abako_bank.viewmodels.NotificationViewModel;
import com.example.abako_bank.viewmodels.NotificationViewModelFactory;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigLimFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigLimFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "device";
    private static final String ARG_PARAM2 = "tipo_juego";
    private static final String ARG_PARAM3 = "imei";
    public static final int DATEPICKER_FRAGMENT=1; // adding this line
    public static final int LIMITADOS_FRAGMENT=2; // adding this line
    public static final int NO_JUEGAN_FRAGMENT=3; // adding this line

    /*private ImageView limitados;

    private ImageView no_juegan;*/


    private EditText tope_bola;

    private EditText tope_centena;

    private EditText tope_parlet;


    private ConfigViewModel configViewModel;


    // TODO: Rename and change types of parameters
    private String device;
    private int tipo_juego;
    private String imei;

    public ConfigLimFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigLimFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigLimFragment newInstance(String param1, int param2, String param3) {
        ConfigLimFragment fragment = new ConfigLimFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
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
                ConfirmationDialog selectProfileDialog = ConfirmationDialog.newInstance(ConfigLimFragment.this, device, imei,"");
                selectProfileDialog.show(fm, "fragment_confirmation");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_config_lim, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //System.out.println("Fragmento"+tipo_juego);

        configViewModel = ViewModelProviders.of(this, new ConfigViewModelFactory())
                .get(ConfigViewModel.class);


        /*limitados = view.findViewById(R.id.limitados);
        limitados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                LimitadosDialog editNameDialogFragment = LimitadosDialog.newInstance(device, tipo_juego);
                editNameDialogFragment.setTargetFragment(ConfigLimFragment.this, LIMITADOS_FRAGMENT);
                editNameDialogFragment.show(getFragmentManager().beginTransaction(), "fragment_limitados_dialog");
            }
        });*/

       /* no_juegan = view.findViewById(R.id.no_juegan);
        no_juegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                NoJueganDialog editNameDialogFragment = NoJueganDialog.newInstance(device, tipo_juego);
                editNameDialogFragment.setTargetFragment(ConfigLimFragment.this, NO_JUEGAN_FRAGMENT);
                editNameDialogFragment.show(getFragmentManager().beginTransaction(), "fragment_no_juegan_dialog");
            }
        });*/



        /*bolas = view.findViewById(R.id.bolas);
        parlets = view.findViewById(R.id.parlets);

        no_juegan_list = view.findViewById(R.id.no_juegan_list);*/

        tope_bola = view.findViewById(R.id.tope_bola);
        tope_centena = view.findViewById(R.id.tope_centena);
        tope_parlet = view.findViewById(R.id.tope_parlet);

        List<Config_Topes_Lim> config_limitados = Config_Topes_Lim.find(Config_Topes_Lim.class, "tipojuego = ?", String.valueOf(tipo_juego));
        if(!config_limitados.isEmpty()){

            /*bolas.setText(config_limitados.get(0).getLimitados_bola_string());
            parlets.setText(config_limitados.get(0).getLimitados_parlet_string());*/
            tope_bola.setText(String.valueOf(config_limitados.get(0).getTope_bola()));
            tope_centena.setText(String.valueOf(config_limitados.get(0).getTope_centena()));
            tope_parlet.setText(String.valueOf(config_limitados.get(0).getTope_parlet()));
            //no_juegan_list.setText(String.valueOf(config_limitados.get(0).getLimitados_no_juegan_string()));
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
            if(tope_bola.getText().length() == 0 || tope_bola.getText().toString().isEmpty()){
                tope_bola.setError("El campo es obligatorio");
            }

            if(tope_parlet.getText().length() == 0 || tope_parlet.getText().toString().isEmpty()){
                tope_parlet.setError("El campo es obligatorio");
            }

            if(tope_centena.getText().length() == 0 || tope_centena.getText().toString().isEmpty()){
                tope_centena.setError("El campo es obligatorio");
            }

            if(tope_centena.getText().length() != 0 && !tope_centena.getText().toString().isEmpty() && tope_parlet.getText().length() != 0 && !tope_parlet.getText().toString().isEmpty()
                    && tope_bola.getText().length() != 0 && !tope_bola.getText().toString().isEmpty()) {
                configViewModel.saveConfigTopes(tipo_juego,tope_bola.getText().toString(),tope_parlet.getText().toString(),tope_centena.getText().toString());

                Toast.makeText(getContext(), "Configuración salvada correctamente", Toast.LENGTH_LONG).show();

                Bundle args = new Bundle();

                args.putString("device", device);
                args.putString("imei", imei);
                args.putString("profile", "Listero");

                NavHostFragment.findNavController(ConfigLimFragment.this)
                        .navigate(R.id.action_configLimFragment_to_configFragment, args);
            }
        }

        return super.onOptionsItemSelected(item);

    }


}