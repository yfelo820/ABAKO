package com.example.abako_bank.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.abako_bank.R;
import com.example.abako_bank.models.Colector;

import java.util.List;

public class SpinnerStringAdapter extends ArrayAdapter<Colector> {
    LayoutInflater flater;

    public SpinnerStringAdapter(@NonNull Activity context, int resource, @NonNull List<Colector> objects) {
        super(context, resource, objects);
        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Colector rowItem = getItem(position);

        View rowview = flater.inflate(android.R.layout.simple_spinner_item,null,true);

        TextView txtTitle = (TextView) rowview.findViewById(android.R.id.text1);
        txtTitle.setText(rowItem.getNombre());


        return rowview;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = flater.inflate(android.R.layout.simple_spinner_item,parent, false);
        }
        Colector rowItem = getItem(position);
        TextView txtTitle = (TextView) convertView.findViewById(android.R.id.text1);
        txtTitle.setText(rowItem.getNombre());

        return convertView;
    }
}
