package com.example.tuongvy_bai14_tabselector2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class myarrayAdapter extends ArrayAdapter<Item> {
    Activity context = null;
    ArrayList<Item> myArray = null;
    int LayoutId;
    public myarrayAdapter(Activity context, int LayoutId, ArrayList<Item> arr) {
        super(context, LayoutId, arr);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.LayoutId = LayoutId;
        this.myArray = arr;

    }
    @Override
    public View getView(int position,View convertView,  ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        convertView= inflater.inflate(LayoutId,null);
        Item item = myArray.get(position);
        ImageView btnlike = convertView.findViewById(R.id.btnlike);
        int thich = item.getThich();
        if(thich==1){
            btnlike.setImageResource(R.drawable.favourite);
        }else{
            btnlike.setImageResource(R.drawable.unlike);
        }
        TextView txtmaso = convertView.findViewById(R.id.txtmaso);
        txtmaso.setText(item.getMaso());
        TextView txttieude = convertView.findViewById(R.id.txttieude);
        txttieude.setText(item.getTieude());
        return convertView;
    }
}