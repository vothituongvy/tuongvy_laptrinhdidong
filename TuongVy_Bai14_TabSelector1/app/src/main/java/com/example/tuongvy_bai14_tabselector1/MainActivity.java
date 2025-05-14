package com.example.tuongvy_bai14_tabselector1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edta, edtb, edtkq;
    Button btncong;
    ListView lv;
    ArrayList<String> list;
    ArrayAdapter<String> myarray;
    TabHost myTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        addControls();
        addEvent();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvent() {
        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(edta.getText().toString());
                int b = Integer.parseInt(edtb.getText().toString());
                int c = a +b;
                edtkq.setText(c+"");
                list.add(a+" + "+b+" = "+c);
                myarray.notifyDataSetChanged();
            }
        });
    }

    private void addControls() {
        edta = findViewById(R.id.edta);
        edtb = findViewById(R.id.edtb);
        edtkq = findViewById(R.id.edtkq);
        btncong = findViewById(R.id.btnTong);
        lv = findViewById(R.id.lv);
        list = new ArrayList<>();
        myarray = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(myarray);
        lv.setAdapter(myarray);
        myTabHost = findViewById(R.id.myTabHost);
        myTabHost.setup();
        TabHost.TabSpec spec1, spec2;
        spec1 = myTabHost.newTabSpec("tab1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("", getResources().getDrawable(R.drawable.cong));
        myTabHost.addTab(spec1);
        spec2 = myTabHost.newTabSpec("tab2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("", getResources().getDrawable(R.drawable.lichsu));
        myTabHost.addTab(spec2);

    }
}