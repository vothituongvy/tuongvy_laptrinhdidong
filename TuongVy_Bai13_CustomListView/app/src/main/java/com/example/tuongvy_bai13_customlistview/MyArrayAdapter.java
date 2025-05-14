package com.example.tuongvy_bai13_customlistview;

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

public class MyArrayAdapter extends ArrayAdapter {
    ///  khai bai 3 tham so của Adapter
    Activity context ;
    int IdLayout ;
    ArrayList<Phone> mylist;

    public  MyArrayAdapter(Activity context , int IdLayout , ArrayList<Phone> mylist){
        super(context , IdLayout , mylist);
        this.IdLayout = IdLayout;
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //tao de chua layout phụ
        LayoutInflater myFacter = context.getLayoutInflater();
        /* dat layout len de tao ra view */
        convertView = myFacter.inflate(IdLayout , null);
        Phone myPhone = mylist.get(position);
        // khai bao anh xa id va hien thi anh
        ImageView img_phone = convertView.findViewById(R.id.img_phone);
        img_phone.setImageResource(myPhone.getImage());
        // khai bao anh xa id va hien thi ten dth
        TextView txt_phone = convertView.findViewById(R.id.txt_phone);
        txt_phone.setText(myPhone.getName());
        TextView txt_money = convertView.findViewById(R.id.txt_money);
        txt_money.setText(myPhone.getMoney());
        // tra ve view
        return convertView;
    }
}
