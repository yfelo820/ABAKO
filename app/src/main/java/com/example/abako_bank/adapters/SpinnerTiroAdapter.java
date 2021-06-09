package com.example.abako_bank.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.abako_bank.models.Colector;
import com.example.abako_bank.models.Tiro;

import java.util.List;

public class SpinnerTiroAdapter extends ArrayAdapter<Tiro> {
    LayoutInflater flater;

    public SpinnerTiroAdapter(@NonNull Activity context, int resource, @NonNull List<Tiro> objects) {
        super(context, resource, objects);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tiro rowItem = getItem(position);

        View rowview = flater.inflate(android.R.layout.simple_spinner_item,null,true);

        TextView txtTitle = (TextView) rowview.findViewById(android.R.id.text1);
        txtTitle.setText(rowItem.toString());


        return rowview;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = flater.inflate(android.R.layout.simple_spinner_item,parent, false);
        }
        Tiro rowItem = getItem(position);
        TextView txtTitle = (TextView) convertView.findViewById(android.R.id.text1);
        txtTitle.setText(rowItem.toString());

        return convertView;
    }
}
