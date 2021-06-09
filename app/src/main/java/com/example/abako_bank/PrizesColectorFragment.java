package com.example.abako_bank;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.prizes.PrizesColectorResponse;
import com.example.abako_bank.api.response.prizes.PrizesResponse;
import com.example.abako_bank.viewmodels.PrizesColectorFragmentViewModel;
import com.example.abako_bank.viewmodels.PrizesColectorViewModelFactory;
import com.example.abako_bank.viewmodels.PrizesViewModel;
import com.example.abako_bank.viewmodels.PrizesViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrizesColectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrizesColectorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "colector_id";
    private static final String ARG_PARAM2 = "tiro_id";

    // TODO: Rename and change types of parameters
    private String tiro_id = null;
    private String colector_id = null;

    private TableLayout tableLayout;
    private ProgressBar progressBar;

    private PrizesColectorFragmentViewModel prizesViewModel;

    public PrizesColectorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrizesColectorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrizesColectorFragment newInstance(String param1, String param2) {
        PrizesColectorFragment fragment = new PrizesColectorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            colector_id = getArguments().getString(ARG_PARAM1);
            tiro_id = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prizes_colector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prizesViewModel = ViewModelProviders.of(getActivity(), new PrizesColectorViewModelFactory())
                .get(PrizesColectorFragmentViewModel.class);

        tableLayout = view.findViewById(R.id.table_layout_prizes);

        progressBar = view.findViewById(R.id.progressBar2);


        prizesViewModel.getPrizeByColector(colector_id, tiro_id);

        prizesViewModel.getDefaultResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DefaultResponse>() {
            @Override
            public void onChanged(DefaultResponse defaultResponse) {
                Toast.makeText(getContext(), defaultResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        prizesViewModel.getPrizesColectorResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<PrizesColectorResponse>() {
            @Override
            public void onChanged(PrizesColectorResponse prizesResponse) {

                progressBar.setVisibility(View.GONE);

                tableLayout.removeAllViews();
                TableRow row1= new TableRow(getContext());
                TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                lp1.setMargins(5,5,5,5);
                row1.setLayoutParams(lp1);

                TextView id_colector_2 = new TextView(getContext());
                id_colector_2.setGravity(Gravity.CENTER);
                id_colector_2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                id_colector_2.setTextColor(getResources().getColor(R.color.leida));
                id_colector_2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                id_colector_2.setText("ID");


                TextView letra_colector  = new TextView(getContext());
                letra_colector.setGravity(Gravity.CENTER);
                letra_colector.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                letra_colector.setTextColor(getResources().getColor(R.color.leida));
                letra_colector.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                letra_colector.setText("L");

                TextView letra_limpio  = new TextView(getContext());
                letra_limpio.setGravity(Gravity.CENTER);
                letra_limpio.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                letra_limpio.setTextColor(getResources().getColor(R.color.leida));
                letra_limpio.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                letra_limpio.setText("LIMPIO");

                TextView letra_premio  = new TextView(getContext());
                letra_premio.setGravity(Gravity.CENTER);
                letra_premio.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                letra_premio.setTextColor(getResources().getColor(R.color.leida));
                letra_premio.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                letra_premio.setText("PREMIO");

                TextView letra_gp  = new TextView(getContext());
                letra_gp.setGravity(Gravity.CENTER);
                letra_gp.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                letra_gp.setTextColor(getResources().getColor(R.color.leida));
                letra_gp.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                letra_gp.setText("G/P");


                row1.addView(id_colector_2);
                row1.addView(letra_colector);
                row1.addView(letra_limpio);
                row1.addView(letra_premio);
                row1.addView(letra_gp);

                tableLayout.addView(row1);

                for(int i = 0; i < prizesResponse.getCollector().getListeros().size(); i++){
                    TableRow row= new TableRow(getContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(20,20,20,20);
                    row.setLayoutParams(lp);

                    row.setClickable(true);

                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TableRow tableRow = (TableRow) view;
                            TextView textView = (TextView) tableRow.getChildAt(0);
                            final Bundle bundle = new Bundle();
                            bundle.putString("lister_id",textView.getText().toString());
                            bundle.putString("tiro_id",tiro_id);
                            NavHostFragment.findNavController(PrizesColectorFragment.this)
                                    .navigate(R.id.action_prizesColectorFragment_to_listasFragment, bundle);

                        }
                    });

                    prizesResponse.getCollector().getListeros().get(i).calcular();

                    TextView id_colector = new TextView(getContext());
                    id_colector.setGravity(Gravity.CENTER);
                    id_colector.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                    id_colector.setTextColor(getResources().getColor(R.color.black));
                    id_colector.setText(String.valueOf(prizesResponse.getCollector().getListeros().get(i).get_id()));


                    TextView letra_c  = new TextView(getContext());
                    letra_c.setGravity(Gravity.CENTER);
                    letra_c.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                    letra_c.setTextColor(getResources().getColor(R.color.black));
                    letra_c.setText(prizesResponse.getCollector().getListeros().get(i).getNombre());


                    TextView limpio  = new TextView(getContext());

                    limpio.setGravity(Gravity.CENTER);
                    limpio.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                    limpio.setTextColor(getResources().getColor(R.color.strongBlue));

                    limpio.setText(prizesResponse.getCollector().getListeros().get(i).getLimpio());

                    TextView premio  = new TextView(getContext());

                    premio.setGravity(Gravity.CENTER);
                    premio.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                    premio.setTextColor(getResources().getColor(R.color.strongGreen));

                    premio.setText(prizesResponse.getCollector().getListeros().get(i).getPremio());

                    TextView gp  = new TextView(getContext());

                    gp.setGravity(Gravity.CENTER);
                    gp.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);

                    double dd = Double.valueOf(prizesResponse.getCollector().getListeros().get(i).getG_p());
                    if(dd < 0){
                        gp.setTextColor(getResources().getColor(R.color.colorPrimary2));
                    }else{
                        gp.setTextColor(getResources().getColor(R.color.strongGreen));
                    }

                    gp.setText(prizesResponse.getCollector().getListeros().get(i).getG_p());


                    row.addView(id_colector);
                    row.addView(letra_c);
                    row.addView(limpio);
                    row.addView(premio);
                    row.addView(gp);


                    tableLayout.addView(row,i+1);
                }

            }
        });
    }
}