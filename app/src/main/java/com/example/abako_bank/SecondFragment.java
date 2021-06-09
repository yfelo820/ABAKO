package com.example.abako_bank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abako_bank.adapters.RecyclerViewNotificationAdapter;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.NotificationResponse;
import com.example.abako_bank.dialogs.DialogFechaFragment;
import com.example.abako_bank.dialogs.TiroDialogFragment;
import com.example.abako_bank.models.Notification;
import com.example.abako_bank.models.Settings;
import com.example.abako_bank.viewmodels.LoginViewModel;
import com.example.abako_bank.viewmodels.LoginViewModelFactory;
import com.example.abako_bank.viewmodels.NotificationViewModel;
import com.example.abako_bank.viewmodels.NotificationViewModelFactory;
import com.example.abako_bank.websocket.App;

import java.util.List;

import static com.mikepenz.iconics.Iconics.getApplicationContext;

public class SecondFragment extends Fragment {
    NotificationViewModel notificationViewModel;
    RecyclerViewNotificationAdapter recyclerViewNotificationAdapter;
    RecyclerView recyclerView;

    private int selected = 0;//0-todas,1-leidas,2-no leidas

    ProgressBar progressBar;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBar2);

        notificationViewModel = ViewModelProviders.of(this, new NotificationViewModelFactory())
                .get(NotificationViewModel.class);

        notificationViewModel.getBankRepository().allNotifications("0", "", "");//mostrar siempre no leidas
        progressBar.setVisibility(View.VISIBLE);

        notificationViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer<NotificationResponse>() {
            @Override
            public void onChanged(NotificationResponse notificationResponse) {
                if(notificationResponse.getCode().equals("200")){
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerViewNotificationAdapter = new RecyclerViewNotificationAdapter((MainActivity) getActivity(),notificationResponse.getData(), SecondFragment.this, notificationViewModel);
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(recyclerViewNotificationAdapter);
                }else if(notificationResponse.getCode().equals("400")){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Error en la petición", Toast.LENGTH_SHORT).show();
                }else if(notificationResponse.getCode().equals("401")){
                    Settings settings = Settings.listAll(Settings.class).get(0);
                    notificationViewModel.getBankRepository().autenticateBank(settings.getUsername(), settings.getPassword(), settings.getDevice_uuid(), settings.getDevice_id());
                    notificationViewModel.getAutenticateResponseLiveData().observe(getViewLifecycleOwner(), new Observer<AutenticateResponse>() {
                        @Override
                        public void onChanged(AutenticateResponse defaultResponse) {
                            progressBar.setVisibility(View.INVISIBLE);
                            System.out.println(defaultResponse.getMessage());
                            if(defaultResponse.getStatus().equals("400")){
                                Toast.makeText(getApplicationContext(),defaultResponse.getMessage(),Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                                System.exit(0);
                            }else if(defaultResponse.getStatus().equals("200")){
                                //todo ok
                                notificationViewModel.getBankRepository().allNotifications("0", "","");
                            }

                        }
                    });
                }

            }
        });

        notificationViewModel.getUpdateNotificacionLiveData().observe(getActivity(), new Observer<DefaultResponse>() {
            @Override
            public void onChanged(DefaultResponse defaultResponse) {
                if(defaultResponse.getStatus().equals("200")){
                    notificationViewModel.getBankRepository().allNotifications("0", "", "");
                    progressBar.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.notification_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_by_date) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            DialogFechaFragment editNameDialogFragment = DialogFechaFragment.newInstance("title");
            editNameDialogFragment.show(fm, "fragment_fecha");
        }/*else if(id == R.id.mostrar_todas){
            progressBar.setVisibility(View.VISIBLE);
            notificationViewModel.getBankRepository().allNotifications("", "", "");
            selected = 0;

        }else if(id == R.id.mostrar_no_leidas){
            progressBar.setVisibility(View.VISIBLE);
            notificationViewModel.getBankRepository().allNotifications("0", "", "");

            selected = 2;
        }else if(id == R.id.mostrar_leidas){
            progressBar.setVisibility(View.VISIBLE);
            notificationViewModel.getBankRepository().allNotifications("1", "", "");

            selected = 1;
        }else if(id == android.R.id.home){
            progressBar.setVisibility(View.VISIBLE);
            if(selected == 0){
                notificationViewModel.getBankRepository().allNotifications("", "", "");
            }else if(selected == 1){
                notificationViewModel.getBankRepository().allNotifications("1", "", "");
            }else if(selected == 2){
                notificationViewModel.getBankRepository().allNotifications("0", "", "");
            }
        }else if(id == R.id.eliminar){
            recyclerViewNotificationAdapter.getSelectedNotificaciones();
        }*/
        return super.onOptionsItemSelected(item);
    }
}