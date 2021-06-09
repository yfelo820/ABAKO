package com.example.abako_bank.models.models2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abako_bank.R;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    ArrayList<Lista1> listDatos;

    public AdapterDatos(ArrayList<Lista1> listDatos)
    {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jugadas_lista_items,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {

        holder.AsignarDatos(listDatos.get(position));

    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView id, limpio, premio, gana;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txtID);
            limpio = itemView.findViewById(R.id.txtLimpio);
            premio = itemView.findViewById(R.id.txtPremio);
            gana = itemView.findViewById(R.id.txtGana);


        }

        public void AsignarDatos(Lista1 param) {

            id.setText(param.id);
            limpio.setText(param.premio.toString());
            premio.setText(param.premio.toString());
            gana.setText(param.gana.toString());
        }
    }
}