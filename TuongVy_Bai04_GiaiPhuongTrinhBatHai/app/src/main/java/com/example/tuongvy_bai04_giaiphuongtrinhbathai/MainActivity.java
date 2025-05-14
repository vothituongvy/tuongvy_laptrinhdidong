package com.example.tuongvy_bai04_giaiphuongtrinhbathai;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button btnTieptuc, btnGiai, btnThoat;
    EditText edita, editb, editc;
    TextView txtkq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnTieptuc = findViewById(R.id.btntieptuc);
        btnGiai = findViewById(R.id.btngiaipt);
        btnThoat = findViewById(R.id.btnthoat);
        edita = findViewById(R.id.edita);
        editb = findViewById(R.id.editb);
        editc = findViewById(R.id.editc);
        txtkq = findViewById(R.id.txtkq);

        btnGiai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String sa = edita.getText().toString().trim();
                    String sb = editb.getText().toString().trim();
                    String sc = editc.getText().toString().trim();

                    if (sa.isEmpty() || sb.isEmpty() || sc.isEmpty()) {
                        txtkq.setText("Vui lòng nhập đầy đủ a, b, c!");
                        return;
                    }

                    double a = Double.parseDouble(sa);
                    double b = Double.parseDouble(sb);
                    double c = Double.parseDouble(sc);

                    DecimalFormat dcf = new DecimalFormat("0.00");
                    String kq = "";

                    if (a == 0) {
                        if (b == 0) {
                            kq = (c == 0) ? "PT vô số nghiệm" : "PT vô nghiệm";
                        } else {
                            kq = "PT có 1 nghiệm: x = " + dcf.format(-c / b);
                        }
                    } else {
                        double delta = b * b - 4 * a * c;
                        if (delta < 0) {
                            kq = "PT vô nghiệm";
                        } else if (delta == 0) {
                            kq = "PT có nghiệm kép: x1 = x2 = " + dcf.format(-b / (2 * a));
                        } else {
                            double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                            double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                            kq = "PT có 2 nghiệm: x1 = " + dcf.format(x1) + ", x2 = " + dcf.format(x2);
                        }
                    }
                    txtkq.setText(kq);
                } catch (NumberFormatException e) {
                    txtkq.setText("Lỗi: Vui lòng nhập số hợp lệ!");
                }
            }
        });

        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edita.setText("");
                editb.setText("");
                editc.setText("");
                txtkq.setText("");
                edita.requestFocus();
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
