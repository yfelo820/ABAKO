package com.example.abako_bank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abako_bank.R;

import java.util.List;

public class RecyclerViewStringAdapter extends RecyclerView.Adapter<RecyclerViewStringAdapter.ViewHolder> {

    FragmentActivity context;
    List<String> userArrayList;

    public RecyclerViewStringAdapter(FragmentActivity context, List<String> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }


    @Override
    public RecyclerViewStringAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.string_item, parent, false);

        // Return a new holder instance
        RecyclerViewStringAdapter.ViewHolder viewHolder = new RecyclerViewStringAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewStringAdapter.ViewHolder holder, final int position) {
        final String user = userArrayList.get(position);
        final RecyclerViewStringAdapter.ViewHolder viewHolder= (RecyclerViewStringAdapter.ViewHolder) holder;

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
            }
        });

        viewHolder.txtView_title.setText(user);

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public List<String> getUserArrayList() {
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
