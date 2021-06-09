package com.example.abako_bank.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.abako_bank.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DialogFechaFragment extends DialogFragment {
    private Button cancelar;
    private Button aceptar;
    private CalendarView calendarView;
    private TextView textView19;
    private TextView textView21;
    private boolean firstDateSelected  = false;

    public DialogFechaFragment() {

        // Empty constructor is required for DialogFragment

        // Make sure not to add arguments to the constructor

        // Use `newInstance` instead as shown below

    }



    public static DialogFechaFragment newInstance(String title) {

        DialogFechaFragment frag = new DialogFechaFragment();

        Bundle args = new Bundle();

        args.putString("title", title);

        frag.setArguments(args);

        return frag;

    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_historial, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Get field from view

        textView19 = view.findViewById(R.id.textView19);
        textView21 = view.findViewById(R.id.textView21);

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
                if(textView19.getText().length() != 0 && textView21.getText().length() != 0){
                    if(validFirstEndDate(textView19.getText().toString(), textView21.getText().toString())){
                        textView19.setError(null);
                        textView21.setError(null);
                        Intent i = new Intent();
                        i.putExtra("changeData",1);
                        i.putExtra("fecha_desde",textView19.getText().toString());
                        i.putExtra("fecha_hasta",textView21.getText().toString());
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                        dismiss();
                    }else{
                        textView19.setError("Las fechas no tiene un rango correcto");
                        textView21.setError("Las fechas no tiene un rango correcto");
                    }
                }



            }
        });

        calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //buscar aqui por fecha las jugadas
                textView19.setError(null);
                textView21.setError(null);
                if(!firstDateSelected){
                    int month = i1+1;
                    textView19.setText(i+"-"+month+"-"+i2);
                    firstDateSelected = true;

                }else {
                    int month = i1+1;
                    textView21.setText(i+"-"+month+"-"+i2);
                    firstDateSelected = false;
                }


            }
        });

        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    boolean validFirstEndDate(String date1, String date2){
        try {
            Date parse1=new SimpleDateFormat("yyyy-MM-dd").parse(date1);

            Date parse2 = new SimpleDateFormat("yyyy-MM-dd").parse(date2);

            if(parse1.before(parse2) && parse2.after(parse1)){
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();

            return false;
        }

        return false;
    }
}
