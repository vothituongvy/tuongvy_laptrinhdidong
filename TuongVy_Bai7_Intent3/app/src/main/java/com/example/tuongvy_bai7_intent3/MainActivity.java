package com.example.tuongvy_bai7_intent3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtA, edtB, edtKQ;
    Button btnrequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        edtA = findViewById(R.id.edta);
        edtB = findViewById(R.id.edtb);
        edtKQ = findViewById(R.id.edtketqua);
        btnrequest = findViewById(R.id.button);
        btnrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Khai báo intent
                Intent myintent = new Intent(MainActivity.this, SubActivity.class);
                //Lấy dữ liệu a, b
                int a = Integer.parseInt(edtA.getText().toString());
                int b = Integer.parseInt(edtB.getText().toString());
                // Đẩy dữ liệu vào Intent
                myintent.putExtra("soa", a);
                myintent.putExtra("sob", b);
                // Khởi động Intent và chờ kết quảtrả về

                startActivityForResult(myintent, 99);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,
                resultCode, data);
        if (requestCode == 99 && resultCode == 33)
        {
            int sum = data.getIntExtra("kq",0);
            edtKQ.setText("Tổng 2 số là: "+sum);
        }
        if (requestCode == 99 && resultCode == 34)
        {
            int sub = data.getIntExtra("kq",0);
            edtKQ.setText("Hiệu 2 số là: "+sub);
        }
    }
}