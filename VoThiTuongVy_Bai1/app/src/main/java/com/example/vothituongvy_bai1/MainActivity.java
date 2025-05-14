package com.example.vothituongvy_bai1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
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


        edtVnd = findViewById(R.id.editTextNumber);
        edtForeign = findViewById(R.id.editTextNumber1);

        rbUsd = findViewById(R.id.radioButton);
        rbEur = findViewById(R.id.radioButton2);
        rbJpy = findViewById(R.id.radioButton3);
        btnVndToForeign = findViewById(R.id.button2);
        btnForeignToVnd = findViewById(R.id.button3);
        btnClear = findViewById(R.id.button4);


        btnVndToForeign.setOnClickListener(v -> convertVndToForeign());


        btnForeignToVnd.setOnClickListener(v -> convertForeignToVnd());


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
