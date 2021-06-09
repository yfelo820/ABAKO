package com.example.abako_bank.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abako_bank.MainActivity;
import com.example.abako_bank.R;
import com.example.abako_bank.api.request.TiroRequest;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;
import com.example.abako_bank.viewmodels.TiroViewModel;
import com.example.abako_bank.viewmodels.TiroViewModelFactory;

import java.util.Calendar;
import java.util.Date;

public class TiroDialogFragment extends DialogFragment {

    private TiroViewModel mViewModel;

    public TiroDialogFragment() {
    }

    public static TiroDialogFragment newInstance() {

        TiroDialogFragment frag = new TiroDialogFragment();

        return frag;

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tiro_dialog, container);

    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height_tiro);
        getDialog().getWindow().setLayout(width, height);


    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity(), new TiroViewModelFactory())
                .get(TiroViewModel.class);

        // Get field from view


        /*final Switch aSwitch = view.findViewById(R.id.switch2);

        final TextView escribir = view.findViewById(R.id.textView12);
        final TextView obtener = view.findViewById(R.id.textView13);*/

        final EditText centena = view.findViewById(R.id.centena);
        final EditText fijo = view.findViewById(R.id.fijo);
        final EditText corrido_1 = view.findViewById(R.id.corrido_1);
        final EditText corrido_2 = view.findViewById(R.id.corrido_2);

        mViewModel.getDefaultResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DefaultResponse>() {
            @Override
            public void onChanged(DefaultResponse defaultResponse) {
                Toast.makeText(getContext(), defaultResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Button button = view.findViewById(R.id.button17);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 12){
                    mViewModel.setPick(new TiroRequest(1,Integer.valueOf(centena.getText().toString()), Integer.valueOf(fijo.getText().toString()),
                            Integer.valueOf(corrido_1.getText().toString()), Integer.valueOf(corrido_2.getText().toString())));
                }else if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 12){
                    mViewModel.setPick(new TiroRequest(2,Integer.valueOf(centena.getText().toString()), Integer.valueOf(fijo.getText().toString()),
                            Integer.valueOf(corrido_1.getText().toString()), Integer.valueOf(corrido_2.getText().toString())));
                }

                dismiss();
            }
        });

        Button button2 = view.findViewById(R.id.button7);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        /*aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aSwitch.isChecked()){
                    obtener.setTextColor(getResources().getColor(R.color.colorPrimary2));
                    escribir.setTextColor(getResources().getColor(R.color.black));
                    centena.setEnabled(false);
                    fijo.setEnabled(false);
                    corrido_1.setEnabled(false);
                    corrido_2.setEnabled(false);
                }else{
                    escribir.setTextColor(getResources().getColor(R.color.colorPrimary2));
                    obtener.setTextColor(getResources().getColor(R.color.black));
                    centena.setEnabled(true);
                    fijo.setEnabled(true);
                    corrido_1.setEnabled(true);
                    corrido_2.setEnabled(true);
                }
            }
        });*/


        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }
}
