// MainActivity.java
package com.example.tuongvy_bai14_tabselector2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    public static final String DATABASE_NAME = "arirang.sqlite";

    EditText edttim;
    ListView lv1, lv2, lv3;
    ArrayList<Item> list1, list2, list3;
    myarrayAdapter myarray1, myarray2, myarray3;
    TabHost tab;
    ImageButton btnxoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processCopy();
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        addControl();
        addTim();
        addEvents();
    }

    private void addControl() {
        btnxoa = findViewById(R.id.btnxoa);
        tab = findViewById(R.id.tabhost);
        tab.setup();

        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("", getResources().getDrawable(R.drawable.search));
        tab.addTab(tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("", getResources().getDrawable(R.drawable.list));
        tab.addTab(tab2);

        TabHost.TabSpec tab3 = tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("", getResources().getDrawable(R.drawable.favourite));
        tab.addTab(tab3);

        edttim = findViewById(R.id.edttim);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        myarray1 = new myarrayAdapter(this, R.layout.listitem, list1);
        myarray2 = new myarrayAdapter(this, R.layout.listitem, list2);
        myarray3 = new myarrayAdapter(this, R.layout.listitem, list3);

        lv1.setAdapter(myarray1);
        lv2.setAdapter(myarray2);
        lv3.setAdapter(myarray3);
    }

    private void addEvents() {
        tab.setOnTabChangedListener(tabId -> {
            if ("t2".equalsIgnoreCase(tabId)) {
                addDanhsach();
            } else if ("t3".equalsIgnoreCase(tabId)) {
                addYeuthich();
            }
        });

        btnxoa.setOnClickListener(v -> edttim.setText(""));
    }

    private void addYeuthich() {
        list3.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE YEUTHICH = 1", null);
        while (c.moveToNext()) {
            list3.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
        }
        c.close();
        myarray3.notifyDataSetChanged();
    }

    private void addDanhsach() {
        list2.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSongList", null);
        while (c.moveToNext()) {
            list2.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
        }
        c.close();
        myarray2.notifyDataSetChanged();
    }

    private void addTim() {
        edttim.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getdata();
            }

            private void getdata() {
                String dulieunhap = edttim.getText().toString();
                list1.clear();
                if (!dulieunhap.isEmpty()) {
                    Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE TENBH1 LIKE ? OR MABH LIKE ?",
                            new String[]{"%" + dulieunhap + "%", "%" + dulieunhap + "%"});
                    while (c.moveToNext()) {
                        list1.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
                    }
                    c.close();
                }
                myarray1.notifyDataSetChanged();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                copyDatabaseFromAssets();
                Toast.makeText(this, "Database copied successfully", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void copyDatabaseFromAssets() throws IOException {
        InputStream myInput = getAssets().open(DATABASE_NAME);
        String outFileName = getDatabasePath();

        File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists()) f.mkdir();

        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
