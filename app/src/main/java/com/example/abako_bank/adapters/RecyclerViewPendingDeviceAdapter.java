package com.example.abako_bank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abako_bank.R;
import com.example.abako_bank.SecondFragment;
import com.example.abako_bank.dialogs.BlackListDeviceFragment;
import com.example.abako_bank.dialogs.SelectProfileDialog;
import com.example.abako_bank.models.Dispositivo;
import com.example.abako_bank.models.Notification;
import com.example.abako_bank.models.PendingDevice;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPendingDeviceAdapter extends RecyclerView.Adapter<RecyclerViewPendingDeviceAdapter.ViewHolder>{
    FragmentActivity context;
    private List<PendingDevice> userArrayList;
    private PendingDevice selected;
    private int selectedPosition;

    public RecyclerViewPendingDeviceAdapter(FragmentActivity context, List<PendingDevice> userArrayList) {
        this.context = context;
        if(userArrayList != null && userArrayList.size() != 0){
            this.userArrayList = userArrayList;
            selected = userArrayList.get(0);
            selectedPosition = 0;
        }else{
            this.userArrayList = new ArrayList<>();
        }



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.pending_device_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PendingDevice user = userArrayList.get(position);
        final ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.device_name.setText(user.getUid_dispositivo());
        viewHolder.device_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = user;
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
        if(selectedPosition == position){
            viewHolder.device_check.setChecked(true);
        }
        else{
            viewHolder.device_check.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView device_name;
        CheckBox device_check;
        LinearLayout linearLayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            device_name = itemView.findViewById(R.id.device_name);
            device_check = itemView.findViewById(R.id.device_check);
            linearLayout = itemView.findViewById(R.id.pending_device);
        }
    }

    public PendingDevice getSelected() {
        return selected;
    }

    public void setSelected(PendingDevice selected) {
        this.selected = selected;
    }
}
