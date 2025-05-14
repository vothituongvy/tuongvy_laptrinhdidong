package com.example.tuongvy_bai11_listview2;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> arraywork;
    ArrayAdapter<String> arrAdapater;
    EditText edtwork, edth, edtm;
    TextView txtdate;
    Button btnwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edth = findViewById(R.id.edthour);
        edtm = findViewById(R.id.edtmin);
        edtwork = findViewById(R.id.edtwork);
        btnwork = findViewById(R.id.btnadd);
        lv = findViewById(R.id.listView1);
        txtdate = findViewById(R.id.txtdate);

        arraywork = new ArrayList<>();
        loadData(); // 🔸 Load dữ liệu từ SharedPreferences

        arrAdapater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraywork);
        lv.setAdapter(arrAdapater);

        Date currentDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
        txtdate.setText("Hôm Nay: " + simpleDateFormat.format(currentDate));

        btnwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtwork.getText().toString().equals("") || edth.getText().toString().equals("")
                        || edtm.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Info missing");
                    builder.setMessage("Please enter all information of the work");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                        }
                    });
                    builder.show();
                } else {
                    String str = edtwork.getText().toString() + " - " + edth.getText().toString() + ":" + edtm.getText().toString();
                    arraywork.add(str);
                    arrAdapater.notifyDataSetChanged();
                    saveData();
                    edth.setText("");
                    edtm.setText("");
                    edtwork.setText("");
                }
            }
        });

        lv.setOnItemClickListener((parent, view, position, id) -> {
            arraywork.remove(position);
            arrAdapater.notifyDataSetChanged();
            saveData();
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("WorkList", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(arraywork);
        editor.putStringSet("workSet", set);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("WorkList", MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("workSet", new HashSet<>());
        arraywork.clear();
        arraywork.addAll(set);
    }
}