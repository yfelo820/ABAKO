package com.example.abako_bank.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.abako_bank.ConfigFragment;
import com.example.abako_bank.FirstFragment;
import com.example.abako_bank.R;
import com.example.abako_bank.SecondFragment;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.BlackListResponse;
import com.example.abako_bank.models.Settings;
import com.example.abako_bank.viewmodels.NotificationViewModel;
import com.example.abako_bank.viewmodels.NotificationViewModelFactory;

import static com.mikepenz.iconics.Iconics.getApplicationContext;

public class SelectProfileDialog extends DialogFragment {

    TextView textView;
    Button cancelar;
    Button agragar;
    static SecondFragment fragmentActivity;

    RadioButton radioButton;
    RadioButton radioButton2;

    public SelectProfileDialog() {

        // Empty constructor is required for DialogFragment

        // Make sure not to add arguments to the constructor

        // Use `newInstance` instead as shown below



    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width_large);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height_profile);
        getDialog().getWindow().setLayout(width, height);
    }



    public static SelectProfileDialog newInstance(String device, String imei, SecondFragment fff) {

        SelectProfileDialog frag = new SelectProfileDialog();

        Bundle args = new Bundle();

        args.putString("device", device);
        args.putString("imei", imei);

        frag.setArguments(args);

        fragmentActivity = fff;



        return frag;

    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.select_profile_dialog, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        final String device = getArguments().getString("device");
        final String imei = getArguments().getString("imei");


        textView = view.findViewById(R.id.dispositivo);

        textView.setText("Dispositivo: "+device);

        cancelar = view.findViewById(R.id.button7);

        agragar = view.findViewById(R.id.button17);

        radioButton = view.findViewById(R.id.radioButton);
        radioButton2 = view.findViewById(R.id.radioButton2);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        agragar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();

                Bundle args = new Bundle();

                args.putString("device", device);
                args.putString("imei", imei);

                if(radioButton.isChecked()){
                    args.putString("profile", "Colector");
                }
                if(radioButton2.isChecked()){
                    args.putString("profile", "Listero");
                }


                NavHostFragment.findNavController(fragmentActivity)
                        .navigate(R.id.action_SecondFragment_to_configFragment,args);


            }
        });

        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }
}
