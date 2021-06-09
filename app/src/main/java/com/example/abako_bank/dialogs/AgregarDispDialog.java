package com.example.abako_bank.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abako_bank.FirstFragment;
import com.example.abako_bank.R;
import com.example.abako_bank.SecondFragment;
import com.example.abako_bank.adapters.RecyclerViewNotificationAdapter;
import com.example.abako_bank.adapters.RecyclerViewPendingDeviceAdapter;
import com.example.abako_bank.api.response.PendingDeviceResponse;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;
import com.example.abako_bank.viewmodels.NotificationViewModel;
import com.example.abako_bank.viewmodels.NotificationViewModelFactory;

public class AgregarDispDialog extends DialogFragment {

    private Button button7;
    private Button button17;


    static FirstFragment firstFragment;

    RadioButton radioButton;
    RadioButton radioButton2;

    RecyclerView recyclerView;

    ProgressBar progressBar5;

    private RecyclerViewPendingDeviceAdapter recyclerViewNotificationAdapter;

    private ConfigViewModel configViewModel;


    public AgregarDispDialog() {
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width_large);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height_500);
        getDialog().getWindow().setLayout(width, height);
    }



    public static AgregarDispDialog newInstance(FirstFragment fragment) {

        AgregarDispDialog frag = new AgregarDispDialog();

        firstFragment = fragment;

        return frag;

    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.agragar_dispositvo_dialog, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        button7 = view.findViewById(R.id.button7);
        button17 = view.findViewById(R.id.button17);

        radioButton = view.findViewById(R.id.radioButton);
        radioButton2 = view.findViewById(R.id.radioButton2);

        recyclerView = view.findViewById(R.id.dispositivos_pendientes);

        progressBar5 = view.findViewById(R.id.progressBar5);

        configViewModel = ViewModelProviders.of(getActivity(), new ConfigViewModelFactory())
                .get(ConfigViewModel.class);


        configViewModel.getPendingdevicesresult().observe(getViewLifecycleOwner(), new Observer<PendingDeviceResponse>() {
            @Override
            public void onChanged(PendingDeviceResponse pendingDeviceResponse) {
                if(pendingDeviceResponse.getStatusCode().equals("400")){
                    progressBar5.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error en la petición", Toast.LENGTH_SHORT).show();
                }else if(pendingDeviceResponse.getStatusCode().equals("200")){
                    recyclerViewNotificationAdapter = new RecyclerViewPendingDeviceAdapter(getActivity(),pendingDeviceResponse.getDevices());
                    progressBar5.setVisibility(View.GONE);
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(recyclerViewNotificationAdapter);
                }

            }
        });

        configViewModel.getPendingDevices();
        progressBar5.setVisibility(View.VISIBLE);

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewNotificationAdapter.getSelected() != null){
                    String imei = recyclerViewNotificationAdapter.getSelected().getImei();
                    String uuid = recyclerViewNotificationAdapter.getSelected().getUid_dispositivo();

                    System.out.println(imei+" "+uuid);

                    if(imei != null && uuid != null){
                        dismiss();

                        Bundle args = new Bundle();

                        args.putString("device", uuid);
                        args.putString("imei", imei);

                        if(radioButton.isChecked()){
                            args.putString("profile", "Colector");
                        }
                        if(radioButton2.isChecked()){
                            args.putString("profile", "Listero");
                        }

                        NavHostFragment.findNavController(firstFragment)
                                .navigate(R.id.action_FirstFragment_to_configFragment, args);
                    }
                }else{
                    Toast.makeText(getContext(), "Seleccione un dispositivo a configurar",Toast.LENGTH_SHORT);
                }



            }
        });




        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }
}
