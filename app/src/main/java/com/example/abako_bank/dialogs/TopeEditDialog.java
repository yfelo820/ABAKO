package com.example.abako_bank.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.abako_bank.models.config.Config_Limitados;
import com.example.abako_bank.models.config.Config_Topes_Lim;
import com.example.abako_bank.viewmodels.ConfigViewModel;
import com.example.abako_bank.viewmodels.ConfigViewModelFactory;
import com.example.abako_bank.viewmodels.NotificationViewModel;
import com.example.abako_bank.viewmodels.NotificationViewModelFactory;

import org.w3c.dom.Text;

import java.util.List;

import static com.mikepenz.iconics.Iconics.getApplicationContext;

public class TopeEditDialog extends DialogFragment {

    private TextView tope_bola_bd;

    private EditText tope_bola_value;

    private TextView tope_parlet_bd;

    private EditText tope_parlet_value;

    private TextView tope_centena_bd;

    private EditText tope_centena_value;

    private String device;

    private int tipo_juego;

    private ImageView image_tope_bola;

    private ImageView image_tope_parlet;

    private ImageView image_tope_centena;

    private ConfigViewModel configViewModel;


    public TopeEditDialog() {

    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width_large);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height_large);
        getDialog().getWindow().setLayout(width, height);
    }



    public static TopeEditDialog newInstance(String device, int tipo_juego) {

        TopeEditDialog frag = new TopeEditDialog();

        Bundle args = new Bundle();

        args.putString("device", device);
        args.putInt("tipo_juego", tipo_juego);

        frag.setArguments(args);

        return frag;

    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.edit_tope_dialog, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        configViewModel = ViewModelProviders.of(this, new ConfigViewModelFactory())
                .get(ConfigViewModel.class);

        if (getArguments() != null) {
            device = getArguments().getString("device");
            tipo_juego = getArguments().getInt("tipo_juego");
        }

        tope_bola_bd = view.findViewById(R.id.tope_bola_bd);

        tope_bola_value = view.findViewById(R.id.tope_bola_value);

        tope_parlet_bd = view.findViewById(R.id.tope_parlet_bd);

        tope_parlet_value = view.findViewById(R.id.tope_parlet_value);

        tope_centena_bd = view.findViewById(R.id.tope_centena_bd);

        tope_centena_value = view.findViewById(R.id.tope_centena_value);

        image_tope_bola = view.findViewById(R.id.image_tope_bola);

        image_tope_centena = view.findViewById(R.id.image_tope_centena);

        image_tope_parlet = view.findViewById(R.id.image_tope_parlet);

        List<Config_Topes_Lim> config_limitados = Config_Topes_Lim.find(Config_Topes_Lim.class, "tipojuego = ?", String.valueOf(tipo_juego));
        if(config_limitados.isEmpty()){
            tope_bola_bd.setText("100");
            tope_bola_value.setText("100");

            tope_parlet_bd.setText("100");
            tope_parlet_value.setText("100");

            tope_centena_bd.setText("100");
            tope_centena_value.setText("100");

            Config_Topes_Lim configLimitados = new Config_Topes_Lim();
            configLimitados.setTipo_juego(tipo_juego);
            configLimitados.setTope_bola(Double.valueOf(tope_bola_value.getText().toString()));
            configLimitados.setTope_centena(Double.valueOf(tope_centena_value.getText().toString()));
            configLimitados.setTope_parlet(Double.valueOf(tope_parlet_value.getText().toString()));
            configLimitados.save();
        }else{
            Config_Topes_Lim configLimitados = config_limitados.get(0);

            tope_bola_bd.setText(String.valueOf(configLimitados.getTope_bola()));
            tope_bola_value.setText(String.valueOf(configLimitados.getTope_bola()));

            tope_parlet_bd.setText(String.valueOf(configLimitados.getTope_parlet()));
            tope_parlet_value.setText(String.valueOf(configLimitados.getTope_parlet()));

            tope_centena_bd.setText(String.valueOf(configLimitados.getTope_centena()));
            tope_centena_value.setText(String.valueOf(configLimitados.getTope_centena()));

        }

        image_tope_parlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Config_Topes_Lim> config_lim = Config_Topes_Lim.find(Config_Topes_Lim.class, "tipojuego = ?", String.valueOf(tipo_juego));
                Config_Topes_Lim configLimitados = config_lim.get(0);
                if(tope_bola_value.getText().length() == 0 || tope_bola_value.getText().toString().isEmpty()){
                    tope_bola_value.setError("El campo es obligatorio");
                }else{
                    configLimitados.setTope_parlet(Double.valueOf(tope_parlet_value.getText().toString()));
                    tope_parlet_bd.setText(tope_parlet_value.getText().toString());
                    configLimitados.save();

                    Intent i = new Intent();
                    i.putExtra("changeData",1);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                }

            }
        });

        image_tope_centena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Config_Topes_Lim> config_lim = Config_Limitados.find(Config_Topes_Lim.class, "tipojuego = ?", String.valueOf(tipo_juego));
                Config_Topes_Lim configLimitados = config_lim.get(0);
                if(tope_centena_value.getText().length() == 0 || tope_centena_value.getText().toString().isEmpty()){
                    tope_centena_value.setError("El campo es obligatorio");
                }else{
                    configLimitados.setTope_centena(Double.valueOf(tope_centena_value.getText().toString()));
                    tope_centena_bd.setText(tope_centena_value.getText().toString());
                    configLimitados.save();
                    Intent i = new Intent();
                    i.putExtra("changeData",1);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                }
            }
        });

        image_tope_bola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Config_Topes_Lim> config_lim = Config_Topes_Lim.find(Config_Topes_Lim.class, "tipojuego = ?", String.valueOf(tipo_juego));
                Config_Topes_Lim configLimitados = config_lim.get(0);
                if(tope_bola_value.getText().length() == 0 || tope_bola_value.getText().toString().isEmpty()){
                    tope_bola_value.setError("El campo es obligatorio");
                }else{
                    configLimitados.setTope_bola(Double.valueOf(tope_bola_value.getText().toString()));
                    tope_bola_bd.setText(tope_bola_value.getText().toString());
                    configLimitados.save();
                    Intent i = new Intent();
                    i.putExtra("changeData",1);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                }
            }
        });



        getDialog().getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }
}
