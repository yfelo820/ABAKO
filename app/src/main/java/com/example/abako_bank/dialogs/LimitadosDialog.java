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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abako_bank.R;

import com.example.abako_bank.adapters.RecyclerViewIntegerAdapter;
import com.example.abako_bank.adapters.RecyclerViewNotificationAdapter;
import com.example.abako_bank.adapters.RecyclerViewStringAdapter;
import com.example.abako_bank.models.Notification;
import com.example.abako_bank.models.config.Config_Limitados;

import java.util.ArrayList;
import java.util.List;

public class LimitadosDialog extends DialogFragment {

    RecyclerViewIntegerAdapter recyclerViewIntegerAdapter;
    RecyclerView recyclerView;
    ImageView add;
    Button acpetar;

    Button cancelar;

    Switch switch_mode;

    EditText value;

    int tipo_juego;

    public LimitadosDialog() {

    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width_large);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height_large_2);
        getDialog().getWindow().setLayout(width, height);
    }


    public static LimitadosDialog newInstance() {

        LimitadosDialog frag = new LimitadosDialog();

        return frag;

    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.edit_limitados_dialog, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.limitados_list);

        add = view.findViewById(R.id.add_number);

        switch_mode = view.findViewById(R.id.switch_mode);



        switch_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){//parlet
                    recyclerViewIntegerAdapter = new RecyclerViewIntegerAdapter(getActivity(),new ArrayList<Integer>(), 1);
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(recyclerViewIntegerAdapter);
                }else if(b == false){//bola
                    recyclerViewIntegerAdapter = new RecyclerViewIntegerAdapter(getActivity(),new ArrayList<Integer>(), 0);
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(recyclerViewIntegerAdapter);
                }
            }
        });

        value = view.findViewById(R.id.value);

        if(switch_mode.isChecked()){
            tipo_juego = 2;
        }else{
            tipo_juego = 1;
        }
        if(switch_mode.isChecked()){
            recyclerViewIntegerAdapter = new RecyclerViewIntegerAdapter(getActivity(),new ArrayList<Integer>(), 1);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(recyclerViewIntegerAdapter);
        }else{
            recyclerViewIntegerAdapter = new RecyclerViewIntegerAdapter(getActivity(),new ArrayList<Integer>(), 0);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(recyclerViewIntegerAdapter);
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(value.getText().length() != 0 && !value.getText().toString().isEmpty()){
                    if(switch_mode.isChecked()){
                        //parlet
                        List<Config_Limitados> config_limitadosList = Config_Limitados.listAll(Config_Limitados.class);
                        if(!config_limitadosList.isEmpty()){
                            Config_Limitados config_limitados = config_limitadosList.get(0);
                            List<Integer> integers = config_limitados.getLimitados_parlet();
                            integers.add(Integer.valueOf(value.getText().toString()));
                            config_limitados.setLimitados_parlet(integers);
                            config_limitados.save();
                            Toast.makeText(getContext(),"Limitados salvados correctamente", Toast.LENGTH_SHORT).show();

                        }else{
                            Config_Limitados config_limitados = new Config_Limitados();
                            List<Integer> integers = new ArrayList<>();
                            integers.add(Integer.valueOf(value.getText().toString()));
                            config_limitados.setLimitados_parlet(integers);
                            config_limitados.save();
                            Toast.makeText(getContext(),"Limitados salvados correctamente", Toast.LENGTH_SHORT).show();


                        }

                        recyclerViewIntegerAdapter = new RecyclerViewIntegerAdapter(getActivity(),new ArrayList<Integer>(), 1);
                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(recyclerViewIntegerAdapter);



                    }else{
                        //bola
                        List<Config_Limitados> config_limitadosList = Config_Limitados.listAll(Config_Limitados.class);
                        if(!config_limitadosList.isEmpty()){
                            Config_Limitados config_limitados = config_limitadosList.get(0);
                            List<Integer> integers = config_limitados.getLimitados_bola();
                            integers.add(Integer.valueOf(value.getText().toString()));
                            config_limitados.setLimitados_bola(integers);
                            config_limitados.save();
                            Toast.makeText(getContext(),"Limitados salvados correctamente", Toast.LENGTH_SHORT).show();

                        }else{
                            Config_Limitados config_limitados = new Config_Limitados();
                            List<Integer> integers = new ArrayList<>();
                            integers.add(Integer.valueOf(value.getText().toString()));
                            config_limitados.setLimitados_bola(integers);
                            config_limitados.save();
                            Toast.makeText(getContext(),"Limitados salvados correctamente", Toast.LENGTH_SHORT).show();

                        }

                        recyclerViewIntegerAdapter = new RecyclerViewIntegerAdapter(getActivity(),new ArrayList<Integer>(), 0);
                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(recyclerViewIntegerAdapter);


                    }

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
