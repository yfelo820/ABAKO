package com.example.abako_bank;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.prizes.PrizesListasResponse;
import com.example.abako_bank.viewmodels.ListasViewModel;
import com.example.abako_bank.viewmodels.ListasViewModelFactory;
import com.example.abako_bank.viewmodels.PrizesViewModel;
import com.example.abako_bank.viewmodels.PrizesViewModelFactory;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "lister_id";
    private static final String ARG_PARAM2 = "tiro_id";

    // TODO: Rename and change types of parameters
    private String tiro_id = null;
    private String lister_id = null;

    private TabLayout tabLayout;

    private ListasViewModel prizesViewModel;

    public ListasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListasFragment newInstance(String param1, String param2) {
        ListasFragment fragment = new ListasFragment();
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
            lister_id = getArguments().getString(ARG_PARAM1);
            tiro_id = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         tabLayout = view.findViewById(R.id.tab_layout);

        prizesViewModel = ViewModelProviders.of(getActivity(), new ListasViewModelFactory())
                .get(ListasViewModel.class);

        prizesViewModel.getPrizeListasByLister(lister_id, tiro_id);

        prizesViewModel.getListasResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<PrizesListasResponse>() {
            @Override
            public void onChanged(PrizesListasResponse prizesListasResponse) {

            }
        });

        prizesViewModel.getDefaultResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<DefaultResponse>() {
            @Override
            public void onChanged(DefaultResponse defaultResponse) {
                Toast.makeText(getContext(), defaultResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

         tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
             @Override
             public void onTabSelected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabUnselected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabReselected(TabLayout.Tab tab) {

             }
         });
    }
}