package com.example.abako_bank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.dialogs.AgregarDispDialog;
import com.example.abako_bank.dialogs.LimitadosDialog;
import com.example.abako_bank.dialogs.LoginDialogFragment;
import com.example.abako_bank.dialogs.NoJueganDialog;
import com.example.abako_bank.dialogs.SelectProfileDialog;
import com.example.abako_bank.dialogs.TiroDialogFragment;
import com.example.abako_bank.models.config.Config_Limitados;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;
import com.example.abako_bank.viewmodels.FirstFragmentVieModelFactory;
import com.example.abako_bank.viewmodels.FirstFragmentViewModel;
import com.example.abako_bank.websocket.App;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {
    LinearLayout tiro;
    LinearLayout layout_config;
    LinearLayout limitados;
    LinearLayout no_juegan;
    LinearLayout layout_premios;
    LinearLayout layout_reportes;
    private FirstFragmentViewModel firstFragmentViewModel;

    public static final int DATEPICKER_FRAGMENT=1; // adding this line
    public static final int LIMITADOS_FRAGMENT=2; // adding this line
    public static final int NO_JUEGAN_FRAGMENT=3; // adding this line

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstFragmentViewModel = ViewModelProviders.of(getActivity(), new FirstFragmentVieModelFactory())
                .get(FirstFragmentViewModel.class);

        tiro = view.findViewById(R.id.tiro);
        tiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TiroDialogFragment editNameDialogFragment = TiroDialogFragment.newInstance();
                editNameDialogFragment.show(fm, "fragment_login_name");
            }
        });

        layout_config = view.findViewById(R.id.layout_config);
        layout_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_configFragment);*/
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AgregarDispDialog selectProfileDialog = AgregarDispDialog.newInstance(FirstFragment.this);
                selectProfileDialog.show(fm, "fragment_select_profile");
            }
        });

        layout_premios = view.findViewById(R.id.layout_premios);
        layout_premios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_premiosFragment);
            }
        });

        limitados = view.findViewById(R.id.layout_limitados);
        limitados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                LimitadosDialog editNameDialogFragment = LimitadosDialog.newInstance();
                editNameDialogFragment.setTargetFragment(FirstFragment.this, LIMITADOS_FRAGMENT);
                editNameDialogFragment.show(getFragmentManager().beginTransaction(), "fragment_limitados_dialog");
            }
        });

        no_juegan = view.findViewById(R.id.no_juegan_ayout);
        no_juegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                NoJueganDialog editNameDialogFragment = NoJueganDialog.newInstance();
                editNameDialogFragment.setTargetFragment(FirstFragment.this, NO_JUEGAN_FRAGMENT);
                editNameDialogFragment.show(getFragmentManager().beginTransaction(), "fragment_no_juegan_dialog");
            }
        });

        layout_reportes = view.findViewById(R.id.layout_reportes);
        layout_reportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_jugadasLista2);

            }
        });



        firstFragmentViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DefaultResponse>() {
            @Override
            public void onChanged(DefaultResponse defaultResponse) {
                Toast.makeText(getContext(),defaultResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        firstFragmentViewModel.getMutableLiveDataNoJuegan().observe(getViewLifecycleOwner(), new Observer<DefaultResponse>() {
            @Override
            public void onChanged(DefaultResponse defaultResponse) {
                Toast.makeText(getContext(),defaultResponse.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        /*view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_sync) {
            return true;
        }else if(id == R.id.action_notification){
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case LIMITADOS_FRAGMENT:
                if(resultCode == Activity.RESULT_OK){
                    Bundle bundle=data.getExtras();
                    Integer resultDate = bundle.getInt("changeData");
                    if(resultDate == 1){
                        /**Enviar limitados arriba*/
                        firstFragmentViewModel.addLimitados();
                    }
                }
                break;
            case NO_JUEGAN_FRAGMENT:
                if(resultCode == Activity.RESULT_OK){
                    Bundle bundle=data.getExtras();
                    Integer resultDate = bundle.getInt("changeData");
                    if(resultDate == 1){
                        firstFragmentViewModel.addNoJuegan();
                    }
                }
                break;
        }



    }
}