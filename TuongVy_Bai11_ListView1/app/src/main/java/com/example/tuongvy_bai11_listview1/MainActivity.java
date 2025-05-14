package com.example.tuongvy_bai11_listview1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView txt_phone;
    ListView lv_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        txt_phone=findViewById(R.id.txt_phone);
        lv_phone=findViewById(R.id.lv_phone);
        String arr_phone[]={"Điện thoại Iphone","Điện thoại Samsung","Điện thoại Oppo","Điện thoại Vivo",
                "Điện thoại Xiaomi","Điện thoại Nokia","Điện thoại Oppo"};
        ArrayAdapter<String> myaAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arr_phone);
        lv_phone.setAdapter(myaAdapter);
        lv_phone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txt_phone.setText("Vị trí "+position+" là của "+arr_phone[position]);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}