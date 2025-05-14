package com.example.tuongvy_bai13_customlistview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //khai bao mang chua anh
    int arr_image[] = {R.drawable.cellphone , R.drawable.htc , R.drawable.ip  , R.drawable.lg , R.drawable.wp , R.drawable.samsung , R.drawable.sky};
    String arr_name[] = {"Điện thoại Cell Phone " , "Điện thoại HTC" , "Điện thoại IPhone" , "Điện thoại LG" , "Điện thoại WP" , "Điện thoại SamSung " , "Điện thoại Sky"};
    String arr_money[] = {"10.000.000" , "20.000.000" , "15.000.000" , "30.000.000" , "4.500.000" , "20.000.000" , "5.000.000"};
    ListView lv ;
    ArrayList<Phone> mylist ;
    MyArrayAdapter myArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listview);
        //tao moi mang chinh và add 2 mang con
        mylist = new ArrayList<>();
        for(int i = 0 ; i <arr_image.length ; i++){
            mylist.add( new Phone (arr_image[i] , arr_name[i] , arr_money[i]));
        }
        myArrayAdapter = new MyArrayAdapter(MainActivity.this , R.layout.activity_layout_item ,mylist);
        lv.setAdapter(myArrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent myintent = new Intent(MainActivity.this, activity_sub.class);
                myintent.putExtra("name", arr_name[i]);
                startActivity(myintent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}