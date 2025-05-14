package com.example.vothituonvy1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText edtVnd, edtForeign;
    private RadioGroup radioGroup;
    private RadioButton rbUsd, rbEur, rbJpy;
    private Button btnVndToForeign, btnForeignToVnd, btnClear;

    private final double USD_RATE = 22280;
    private final double EUR_RATE = 24280;
    private final double JPY_RATE = 204;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần
        edtVnd = findViewById(R.id.edtVnd);
        edtForeign = findViewById(R.id.edtForeign);
        radioGroup = findViewById(R.id.radioGroup);
        rbUsd = findViewById(R.id.rbUsd);
        rbEur = findViewById(R.id.rbEur);
        rbJpy = findViewById(R.id.rbJpy);
        btnVndToForeign = findViewById(R.id.btnVndToForeign);
        btnForeignToVnd = findViewById(R.id.btnForeignToVnd);
        btnClear = findViewById(R.id.btnClear);

        // Xử lý chuyển đổi từ VND sang ngoại tệ
        btnVndToForeign.setOnClickListener(v -> convertVndToForeign());

        // Xử lý chuyển đổi từ ngoại tệ sang VND
        btnForeignToVnd.setOnClickListener(v -> convertForeignToVnd());

        // Xóa dữ liệu
        btnClear.setOnClickListener(v -> {
            edtVnd.setText("");
            edtForeign.setText("");
        });
    }

    private double getExchangeRate() {
        if (rbUsd.isChecked()) return USD_RATE;
        if (rbEur.isChecked()) return EUR_RATE;
        if (rbJpy.isChecked()) return JPY_RATE;
        return 1;
    }

    private void convertVndToForeign() {
        String vndStr = edtVnd.getText().toString();
        if (!vndStr.isEmpty()) {
            double vnd = Double.parseDouble(vndStr);
            double result = vnd / getExchangeRate();
            edtForeign.setText(String.format("%.1f", result));
        }
    }

    private void convertForeignToVnd() {
        String foreignStr = edtForeign.getText().toString();
        if (!foreignStr.isEmpty()) {
            double foreign = Double.parseDouble(foreignStr);
            double result = foreign * getExchangeRate();
            edtVnd.setText(String.format("%.1f", result));
        }
    }

    // Xử lý khi nhấn phím Back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Question?")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
