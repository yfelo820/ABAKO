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

import com.example.abako_bank.dialogs.ConfirmationDialog;
import com.example.abako_bank.models.config.Config_Dias_Inactivo;
import com.example.abako_bank.models.config.Config_Horario_Dia;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiasInactivoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiasInactivoFragment extends Fragment {

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


    private EditText dias_activo;


    public DiasInactivoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiasInactivoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiasInactivoFragment newInstance(String param1, String param2, String param3) {
        DiasInactivoFragment fragment = new DiasInactivoFragment();
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
                ConfirmationDialog selectProfileDialog = ConfirmationDialog.newInstance(DiasInactivoFragment.this, device, imei, "");
                selectProfileDialog.show(fm, "fragment_confirmation");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_dias_inactivo, container, false);
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
            if(dias_activo.getText().length() == 0 || dias_activo.getText().toString().isEmpty()){
                dias_activo.setError("El campo es obligatorio");
            }


            if(dias_activo.getText().length() != 0 && !dias_activo.getText().toString().isEmpty() ) {

                configViewModel.saveDiasInactivo(Integer.valueOf(dias_activo.getText().toString()));

                Toast.makeText(getContext(), "Configuración salvada correctamente", Toast.LENGTH_LONG).show();

                Bundle args = new Bundle();

                args.putString("device", device);
                args.putString("imei", imei);
                args.putString("profile", "Colector");

                NavHostFragment.findNavController(DiasInactivoFragment.this)
                        .navigate(R.id.action_diasInactivoFragment_to_configFragment, args);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configViewModel = ViewModelProviders.of(this, new ConfigViewModelFactory())
                .get(ConfigViewModel.class);

        dias_activo = view.findViewById(R.id.inactivo);


        List<Config_Dias_Inactivo> config_dias_inactivos = Config_Dias_Inactivo.listAll(Config_Dias_Inactivo.class);
        if(!config_dias_inactivos.isEmpty()){

            dias_activo.setText(String.valueOf(config_dias_inactivos.get(0).getDias_inactivo()));

        }
    }
}