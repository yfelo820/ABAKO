package com.example.abako_bank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abako_bank.R;
import com.example.abako_bank.models.config.Config_Limitados;
import com.example.abako_bank.models.config.Config_No_Juegan;

import java.util.List;

public class RecyclerViewIntegerAdapter extends RecyclerView.Adapter<RecyclerViewIntegerAdapter.ViewHolder> {

    FragmentActivity context;
    List<Integer> userArrayList;
    int tipo;

    public RecyclerViewIntegerAdapter(FragmentActivity context, List<Integer> userArrayList, int tipo) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.tipo = tipo;

        if(tipo == 1){
            List<Config_Limitados> config_limitadosList = Config_Limitados.listAll(Config_Limitados.class);
            if(!config_limitadosList.isEmpty()){
                this.userArrayList = config_limitadosList.get(0).getLimitados_parlet();
            }
        }else if(tipo == 0){
            List<Config_Limitados> config_limitadosList = Config_Limitados.listAll(Config_Limitados.class);
            if(!config_limitadosList.isEmpty()){
                this.userArrayList = config_limitadosList.get(0).getLimitados_bola();
            }
        }else if(tipo == 2){
            List<Config_No_Juegan> config_limitadosList = Config_No_Juegan.listAll(Config_No_Juegan.class);
            if(!config_limitadosList.isEmpty()){
                this.userArrayList = config_limitadosList.get(0).getNo_juegan();
            }
        }

    }


    @Override
    public RecyclerViewIntegerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.string_item, parent, false);

        // Return a new holder instance
        RecyclerViewIntegerAdapter.ViewHolder viewHolder = new RecyclerViewIntegerAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewIntegerAdapter.ViewHolder holder, final int position) {
        final Integer user = userArrayList.get(position);
        final RecyclerViewIntegerAdapter.ViewHolder viewHolder= (RecyclerViewIntegerAdapter.ViewHolder) holder;

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.delete.setVisibility(View.VISIBLE);
            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userArrayList.remove(position);
                notifyDataSetChanged();
                if(tipo == 0){
                    //borrar bola
                    List<Config_Limitados> config_limitadosList = Config_Limitados.listAll(Config_Limitados.class);
                    Config_Limitados config_limitados = config_limitadosList.get(0);
                    config_limitados.setLimitados_bola(userArrayList);
                    config_limitados.save();
                }else if(tipo == 1){
                    //borrar parlet
                    List<Config_Limitados> config_limitadosList = Config_Limitados.listAll(Config_Limitados.class);
                    Config_Limitados config_limitados = config_limitadosList.get(0);
                    config_limitados.setLimitados_parlet(userArrayList);
                    config_limitados.save();
                }else if(tipo == 2){
                    //borrar no_juegan
                    List<Config_No_Juegan> config_limitadosList = Config_No_Juegan.listAll(Config_No_Juegan.class);
                    Config_No_Juegan config_limitados = config_limitadosList.get(0);
                    config_limitados.setNo_juegan(userArrayList);
                    config_limitados.save();
                }
            }
        });

        viewHolder.txtView_title.setText(user.toString());

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public List<Integer> getUserArrayList() {
        return userArrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtView_title;
        ImageView delete;
        LinearLayout linearLayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtView_title = itemView.findViewById(R.id.numero);
            delete = itemView.findViewById(R.id.delete);
            linearLayout = itemView.findViewById(R.id.fila);
        }
    }
}
