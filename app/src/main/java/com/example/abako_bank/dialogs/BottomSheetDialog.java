package com.example.abako_bank.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.abako_bank.FirstFragment;
import com.example.abako_bank.R;
import com.example.abako_bank.adapters.SpinnerStringAdapter;
import com.example.abako_bank.adapters.SpinnerTiroAdapter;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.TiroResponse;
import com.example.abako_bank.interfaces.BottomSheetDataInterface;
import com.example.abako_bank.models.Tiro;
import com.example.abako_bank.viewmodels.BottomSheetViewModel;
import com.example.abako_bank.viewmodels.BottomSheetViewModelFactory;
import com.example.abako_bank.viewmodels.PrizesViewModel;
import com.example.abako_bank.viewmodels.PrizesViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    String[] country = { "AM", "PM"};
    public static final int DATEPICKER_FRAGMENT=1; // adding this line
    private TextView textView;
    private BottomSheetViewModel bottomSheetViewModel;
    private Spinner spinner;
    private List<Tiro> tiros;
    private TextView spin;
    private String id_tiro_selected = null;
    private BottomSheetDataInterface callback;

    public BottomSheetDialog(BottomSheetDataInterface bottomSheetDataInterface) {
        this.callback=bottomSheetDataInterface;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.filters_layout,
                container, false);

        tiros = new ArrayList<>();

        bottomSheetViewModel = ViewModelProviders.of(getActivity(), new BottomSheetViewModelFactory())
                .get(BottomSheetViewModel.class);

        Button buscar = v.findViewById(R.id.button17);
        Button close = v.findViewById(R.id.button7);
        LinearLayout tiro_fecha = v.findViewById(R.id.tiro_fecha);

        spin = (TextView) v.findViewById(R.id.tiro_tirada);
        spin.setText("Ninguno");

        textView = v.findViewById(R.id.tiro_fecha_text);

        spinner = (Spinner) v.findViewById(R.id.tiro_spinner);

        tiro_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DialogFechaFragment editNameDialogFragment = DialogFechaFragment.newInstance("title");
                editNameDialogFragment.setTargetFragment(BottomSheetDialog.this, DATEPICKER_FRAGMENT);
                editNameDialogFragment.show(fm, "fragment_fecha");
            }
        });

        SpinnerTiroAdapter adapter = new SpinnerTiroAdapter(getActivity(), android.R.layout.simple_spinner_item,new ArrayList<Tiro>());
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        bottomSheetViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<TiroResponse>() {
            @Override
            public void onChanged(TiroResponse tiro) {

                tiros = tiro.getPicks();
                SpinnerTiroAdapter adapter = new SpinnerTiroAdapter(getActivity(), android.R.layout.simple_spinner_item,tiro.getPicks());
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);
            }
        });

        bottomSheetViewModel.getDefaultResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DefaultResponse>() {
            @Override
            public void onChanged(DefaultResponse defaultResponse) {
                Toast.makeText(getContext(), defaultResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Tiro t = tiros.get(i);
                id_tiro_selected = String.valueOf(t.get_id());
                if(t.getTipo_tirada() == 1){
                    spin.setText("AM");
                }else if(t.getTipo_tirada() == 2){
                    spin.setText("PM");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                callback.callbackMethod(id_tiro_selected);

                dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case DATEPICKER_FRAGMENT:
                if(resultCode == Activity.RESULT_OK){
                    Bundle bundle=data.getExtras();
                    Integer resultDate = bundle.getInt("changeData");
                    if(resultDate == 1){
                        String fecha_desde = bundle.getString("fecha_desde");
                        String fecha_hasta = bundle.getString("fecha_hasta");

                        bottomSheetViewModel.getPicks(fecha_desde, fecha_hasta, true);

                        textView.setText(fecha_desde+"/"+fecha_hasta);

                    }
                }
                break;

        }
    }
}
