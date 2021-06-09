package com.example.abako_bank.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abako_bank.R;
import com.example.abako_bank.adapters.RecyclerViewIntegerAdapter;
import com.example.abako_bank.models.config.Config_Limitados;
import com.example.abako_bank.models.config.Config_No_Juegan;

import java.util.ArrayList;
import java.util.List;

public class NoJueganDialog extends DialogFragment {

    RecyclerViewIntegerAdapter recyclerViewIntegerAdapter;
    RecyclerView recyclerView;
    ImageView add;
    Button acpetar;

    Button cancelar;

    EditText value;

    int tipo_juego;
    String device;

    public NoJueganDialog() {

    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width_large);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height_large_2);
        getDialog().getWindow().setLayout(width, height);
    }


    public static NoJueganDialog newInstance() {

        NoJueganDialog frag = new NoJueganDialog();



        return frag;

    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.edit_no_juegan, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            device = getArguments().getString("device");
            tipo_juego = getArguments().getInt("tipo_juego");
        }

        recyclerView = view.findViewById(R.id.limitados_list);

        add = view.findViewById(R.id.add_number);


        value = view.findViewById(R.id.value);

        recyclerViewIntegerAdapter = new RecyclerViewIntegerAdapter(getActivity(),new ArrayList<Integer>(), 2);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(recyclerViewIntegerAdapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(value.getText().length() != 0 && !value.getText().toString().isEmpty()){
                    List<Config_No_Juegan> config_limitadosList = Config_Limitados.listAll(Config_No_Juegan.class);
                    if(!config_limitadosList.isEmpty()){
                        Config_No_Juegan config_limitados = config_limitadosList.get(0);
                        List<Integer> integers = config_limitados.getNo_juegan();
                        integers.add(Integer.valueOf(value.getText().toString()));
                        config_limitados.setNo_juegan(integers);
                        config_limitados.save();

                    }else{
                        Config_No_Juegan config_limitados = new Config_No_Juegan();
                        List<Integer> integers = new ArrayList<>();
                        integers.add(Integer.valueOf(value.getText().toString()));
                        config_limitados.setNo_juegan(integers);
                        config_limitados.save();

                    }

                    recyclerViewIntegerAdapter = new RecyclerViewIntegerAdapter(getActivity(),new ArrayList<Integer>(), 2);
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(recyclerViewIntegerAdapter);

                    value.setText("");
                }

            }
        });



        acpetar = view.findViewById(R.id.button17);
        acpetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //actualizar
                Intent i = new Intent();
                i.putExtra("changeData",1);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                dismiss();
            }
        });

        cancelar = view.findViewById(R.id.button7);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }
}
