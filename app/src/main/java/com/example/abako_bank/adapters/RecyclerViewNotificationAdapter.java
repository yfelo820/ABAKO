package com.example.abako_bank.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abako_bank.ConfigFragment;
import com.example.abako_bank.FirstFragment;
import com.example.abako_bank.MainActivity;
import com.example.abako_bank.R;
import com.example.abako_bank.SecondFragment;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.dialogs.BlackListDeviceFragment;
import com.example.abako_bank.dialogs.SelectProfileDialog;
import com.example.abako_bank.models.Dispositivo;
import com.example.abako_bank.models.Notification;
import com.example.abako_bank.viewmodels.NotificationViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewNotificationAdapter extends RecyclerView.Adapter<RecyclerViewNotificationAdapter.ViewHolder>{
    MainActivity context;
    List<Notification> userArrayList;
    SecondFragment secondFragment;
    NotificationViewModel notificationViewModel;

    public RecyclerViewNotificationAdapter(MainActivity context, List<Notification> userArrayList, SecondFragment secondFragment, NotificationViewModel notificationViewModel) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.secondFragment = secondFragment;
        this.notificationViewModel = notificationViewModel;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.notification_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Notification user = userArrayList.get(position);
        final ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.txtView_title.setText(user.getMensaje());
        viewHolder.fecha.setText(user.getFecha());
        if(user.isEstado()){
            viewHolder.linearLayout.setBackgroundResource(R.color.leida);
        }else{
            viewHolder.linearLayout.setBackgroundResource(R.color.no_leida);
        }
        /*viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.checkBox.isChecked()){
                    if(!selectedNotificaciones.contains(user)){
                        selectedNotificaciones.add(user);
                    }
                }else if(!viewHolder.checkBox.isChecked()){
                    if(selectedNotificaciones.contains(user)){
                        selectedNotificaciones.remove(user);
                    }
                }

            }
        });*/
        /*viewHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                viewHolder.checkBox.setVisibility(View.VISIBLE);
                context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                return false;
            }
        });*/

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(viewHolder.checkBox.getVisibility() != View.VISIBLE){
                    if(user.getTipo().equals("seguridad")){
                        //mostrar dialog de agregar dispositivo en lista negra
                        FragmentManager fm = context.getSupportFragmentManager();
                        BlackListDeviceFragment editNameDialogFragment = BlackListDeviceFragment.newInstance(user.getUid_dispositivo(),user.getImei());
                        editNameDialogFragment.show(fm, "fragment_black_list");
                    }else if(user.getTipo().equals("nuevo_ingreso")){

                        Dispositivo dispositivo = user.getDispositivo();
                        if(dispositivo != null){
                            FragmentManager fm = context.getSupportFragmentManager();
                            SelectProfileDialog selectProfileDialog = SelectProfileDialog.newInstance(dispositivo.getUid_dispositivo(),dispositivo.getImei(), secondFragment);
                            selectProfileDialog.show(fm, "fragment_select_profile");
                        }


                    }
                    notificationViewModel.updateNotificacion(user.get_id());
                }

            //}
        });



    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtView_title;
        TextView fecha;
        CheckBox checkBox;
        LinearLayout linearLayout;


        public ViewHolder(final View itemView) {
            super(itemView);
            txtView_title = itemView.findViewById(R.id.textView2);
            fecha = itemView.findViewById(R.id.textView3);
            checkBox = itemView.findViewById(R.id.checkBox);
            linearLayout = itemView.findViewById(R.id.layout_notification);
        }
    }

}
