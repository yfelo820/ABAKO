package com.example.abako_bank.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abako_bank.ConfigFragment;
import com.example.abako_bank.ConfigHDiaFragment;
import com.example.abako_bank.ConfigHNocheFragment;
import com.example.abako_bank.ConfigLetraFragment;
import com.example.abako_bank.ConfigLetraListeroFragment;
import com.example.abako_bank.ConfigLimFragment;
import com.example.abako_bank.ConfigPagosFragment;
import com.example.abako_bank.ConfigPagosLimFragment;
import com.example.abako_bank.ConfigPorcentPagosFragment;
import com.example.abako_bank.DiasInactivoFragment;
import com.example.abako_bank.FirstFragment;
import com.example.abako_bank.R;
import com.example.abako_bank.UserPassFragment;
import com.example.abako_bank.adapters.RecyclerViewPendingDeviceAdapter;
import com.example.abako_bank.api.response.PendingDeviceResponse;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;
import com.orm.SugarRecord;

public class ConfirmationDialog extends DialogFragment {

    private Button button7; //cancelar
    private Button button17; //acpetar

    private static Fragment fragment;

    private static String _uuid;

    private static String _imei;

    private static String _profile;


    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height_small);
        getDialog().getWindow().setLayout(width, height);
    }



    public static ConfirmationDialog newInstance(Fragment action, String uuid, String imei, String profile) {

        ConfirmationDialog frag = new ConfirmationDialog();

        fragment = action;

        _uuid = uuid;

        _imei = imei;

        _profile = profile;

        return frag;

    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.confirmation_dialog, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        button7 = view.findViewById(R.id.button7);
        button17 = view.findViewById(R.id.button17);



        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();

                args.putString("device", _uuid);
                args.putString("imei", _imei);

                if(fragment instanceof ConfigHDiaFragment){
                    args.putString("profile", "Listero");

                    NavHostFragment.findNavController(ConfirmationDialog.this)
                            .navigate(R.id.action_configHDiaFragment_to_configFragment, args);
                }
                if(fragment instanceof ConfigHNocheFragment){

                    args.putString("profile", "Listero");
                    NavHostFragment.findNavController(ConfirmationDialog.this)
                            .navigate(R.id.action_configHNocheFragment_to_configFragment, args);
                }
                if(fragment instanceof ConfigLetraFragment){
                    args.putString("profile", "Colector");

                    NavHostFragment.findNavController(ConfirmationDialog.this)
                            .navigate(R.id.action_configLetraFragment_to_configFragment, args);
                }

                if(fragment instanceof ConfigLetraListeroFragment){
                    args.putString("profile", "Listero");

                    NavHostFragment.findNavController(ConfirmationDialog.this)
                            .navigate(R.id.action_configLetraListeroFragment_to_configFragment, args);
                }
                if(fragment instanceof ConfigLimFragment){
                    args.putString("profile", "Listero");

                    NavHostFragment.findNavController(ConfirmationDialog.this)
                            .navigate(R.id.action_configLimFragment_to_configFragment, args);
                }
                if(fragment instanceof ConfigPagosFragment){
                    args.putString("profile", "Listero");

                    NavHostFragment.findNavController(ConfirmationDialog.this)
                            .navigate(R.id.action_configPagosFragment2_to_configFragment, args);
                }
                if(fragment instanceof ConfigPagosLimFragment){
                    args.putString("profile", "Listero");

                    NavHostFragment.findNavController(ConfirmationDialog.this)
                            .navigate(R.id.action_configPagosLimFragment_to_configFragment, args);
                }

                if(fragment instanceof ConfigPorcentPagosFragment){
                    args.putString("profile", "Listero");

                    NavHostFragment.findNavController(ConfirmationDialog.this)
                            .navigate(R.id.action_configPagosFragment_to_configFragment, args);
                }
                if(fragment instanceof DiasInactivoFragment){
                    args.putString("profile", "Colector");

                    NavHostFragment.findNavController(ConfirmationDialog.this)
                            .navigate(R.id.action_diasInactivoFragment_to_configFragment, args);
                }

                if(fragment instanceof UserPassFragment){
                    args.putString("profile", _profile);

                    NavHostFragment.findNavController(ConfirmationDialog.this)
                            .navigate(R.id.action_userPassFragment_to_configFragment, args);
                }
                dismiss();


            }
        });




        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }


}
