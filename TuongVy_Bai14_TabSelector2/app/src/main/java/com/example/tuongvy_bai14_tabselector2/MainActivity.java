package com.example.tuongvy_bai14_tabselector2;

import android.os.Bundle;
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
    EditText edttim;
    ListView lv1,lv2,lv3;
    TabHost tab;
    ArrayList<Item> list1, list2, list3;
    myarrayAdapter myarray1, myarray2, myarray3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        addControll();
        addEvent();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvent() {
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("t1")) {
                    list1.clear();
                    list1.add(new Item("52300", "Em là ai Tôi là ai", 0));
                    list1.add(new Item("52600", "Chén Đắng", 1));
                    myarray1.notifyDataSetChanged();

                }
                if (tabId.equalsIgnoreCase("t2")) {
                    list2.clear();
                    list2.add(new Item("57236", "Gởi em ở cuối sông hồng", 0));
                    list2.add(new Item("51548", "Say tình", 0));
                    myarray2.notifyDataSetChanged();
                }
                if (tabId.equalsIgnoreCase("t3")) {
                    list3.clear();
                    list3.add(new Item("57689", "Hát với dòng sông", 1));
                    list3.add(new Item("58716", "Say tình _ Remix", 0));
                    myarray3.notifyDataSetChanged();
                }
            }
        });
    }

    private void addControll() {
        edttim = findViewById(R.id.edttim);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);
        tab=(TabHost)findViewById(R.id.tabHost);
        tab.setup();
        TabHost.TabSpec tab1=tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("",getResources().getDrawable(R.drawable.search));
        tab.addTab(tab1);
        TabHost.TabSpec tab2=tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("",getResources().getDrawable(R.drawable.list));
        tab.addTab(tab2);
        TabHost.TabSpec tab3=tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("",getResources().getDrawable(R.drawable.favourite));
        tab.addTab(tab3);
        list1=new ArrayList<Item>();
        list2=new ArrayList<Item>();
        list3=new ArrayList<Item>();
        myarray1=new myarrayAdapter(MainActivity.this,R.layout.listitem,list1);
        myarray2=new myarrayAdapter(MainActivity.this,R.layout.listitem,list2);
        myarray3=new myarrayAdapter(MainActivity.this,R.layout.listitem,list3);
        lv1.setAdapter(myarray1);
        lv2.setAdapter(myarray2);
        lv3.setAdapter(myarray3);
    }
}