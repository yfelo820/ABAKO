package com.example.abako_bank.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abako_bank.R;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.BlackListResponse;
import com.example.abako_bank.models.Settings;
import com.example.abako_bank.viewmodels.NotificationViewModel;
import com.example.abako_bank.viewmodels.NotificationViewModelFactory;

import static com.mikepenz.iconics.Iconics.getApplicationContext;

public class BlackListDeviceFragment extends DialogFragment {
    private Button cancelar;
    private Button aceptar;
    private TextView textView;
    private ProgressBar progressBar4;

    NotificationViewModel notificationViewModel;

    private String device;

    public BlackListDeviceFragment() {

        // Empty constructor is required for DialogFragment

        // Make sure not to add arguments to the constructor

        // Use `newInstance` instead as shown below



    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width_lista);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height_lista);
        getDialog().getWindow().setLayout(width, height);
    }



    public static BlackListDeviceFragment newInstance(String device, String imei) {

        BlackListDeviceFragment frag = new BlackListDeviceFragment();

        Bundle args = new Bundle();

        args.putString("device", device);
        args.putString("imei", imei);

        frag.setArguments(args);

        return frag;

    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.black_list_dialog, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        notificationViewModel = ViewModelProviders.of(this, new NotificationViewModelFactory())
                .get(NotificationViewModel.class);


        // Get field from view

        final String device = getArguments().getString("device");
        final String imei = getArguments().getString("imei");



        progressBar4 = view.findViewById(R.id.progressBar4);

        cancelar = view.findViewById(R.id.button7);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        aceptar = view.findViewById(R.id.button17);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar4.setVisibility(View.VISIBLE);
                notificationViewModel.getBankRepository().addDeviceToBlackList(device, imei);
                notificationViewModel.getBlackListResponseLiveData().observe(getViewLifecycleOwner(), new Observer<BlackListResponse>() {
                    @Override
                    public void onChanged(BlackListResponse blackListResponse) {

                        if(blackListResponse.getCode() == 200){
                            progressBar4.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(),blackListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            dismiss();
                        }else if(blackListResponse.getCode() == 400){
                            progressBar4.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(),blackListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            dismiss();
                        }else if(blackListResponse.getCode() == 401){
                            Settings settings = Settings.listAll(Settings.class).get(0);
                            notificationViewModel.getBankRepository().autenticateBank(settings.getUsername(), settings.getPassword(), settings.getDevice_uuid(), settings.getDevice_id());
                            notificationViewModel.getAutenticateResponseLiveData().observe(getViewLifecycleOwner(), new Observer<AutenticateResponse>() {
                                @Override
                                public void onChanged(AutenticateResponse defaultResponse) {
                                    progressBar4.setVisibility(View.INVISIBLE);
                                    System.out.println(defaultResponse.getMessage());
                                    if(defaultResponse.getStatus().equals("400")){
                                        Toast.makeText(getApplicationContext(),defaultResponse.getMessage(),Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
                                        System.exit(0);
                                    }else if(defaultResponse.getStatus().equals("200")){
                                        //todo ok
                                        notificationViewModel.getBankRepository().addDeviceToBlackList(device, imei);
                                        dismiss();
                                    }

                                }
                            });
                        }
                    }
                });
            }
        });

        textView = view.findViewById(R.id.textView18);

        textView.setText(device);



        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }
}
