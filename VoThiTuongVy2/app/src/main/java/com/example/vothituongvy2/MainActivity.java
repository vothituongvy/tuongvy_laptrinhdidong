package com.example.vothituongvy2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private EditText edtCustomerName, edtQuantity, edtTotal, edtStatistics;
    private CheckBox cbVip;
    private Button btnCalculate, btnNext, btnStatistics;
    private ImageButton btnExit;

    private int totalCustomers = 0;
    private int totalVipCustomers = 0;
    private double totalRevenue = 0;
    private final double BOOK_PRICE = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtTotal = findViewById(R.id.edtTotal);
        edtStatistics = findViewById(R.id.edtStatistics);
        cbVip = findViewById(R.id.cbVip);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnNext = findViewById(R.id.btnNext);
        btnStatistics = findViewById(R.id.btnStatistics);
        btnExit = findViewById(R.id.btnExit);

        // Set click listeners
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotal();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCustomer();
            }
        });

        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatistics();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });
    }

    private void calculateTotal() {
        try {
            int quantity = Integer.parseInt(edtQuantity.getText().toString());
            double total = quantity * BOOK_PRICE;

            if (cbVip.isChecked()) {
                total *= 0.9; // 10% discount for VIP
            }

            edtTotal.setText(String.format("%,.0f VND", total));

        } catch (NumberFormatException e) {
            edtTotal.setText("Vui lòng nhập số lượng hợp lệ");
        }
    }

    private void nextCustomer() {
        // Save current customer info if total is calculated
        if (!edtTotal.getText().toString().isEmpty() && !edtTotal.getText().toString().equals("Vui lòng nhập số lượng hợp lệ")) {
            totalCustomers++;
            if (cbVip.isChecked()) {
                totalVipCustomers++;
            }

            try {
                String totalStr = edtTotal.getText().toString().replace(" VND", "").replace(",", "");
                double currentTotal = Double.parseDouble(totalStr);
                totalRevenue += currentTotal;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Clear fields for next customer
        edtCustomerName.setText("");
        edtQuantity.setText("");
        edtTotal.setText("");
        cbVip.setChecked(false);

        // Focus on customer name field
        edtCustomerName.requestFocus();
    }

    private void showStatistics() {
        String stats = "Tổng số khách hàng: " + totalCustomers + "\n" +
                "Tổng số KH VIP: " + totalVipCustomers + "\n" +
                "Tổng doanh thu: " + String.format("%,.0f VND", totalRevenue);

        edtStatistics.setText(stats);
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận thoát")
                .setMessage("Bạn có chắc chắn muốn thoát ứng dụng?")
                .setPositiveButton("Có", (dialog, which) -> finish())
                .setNegativeButton("Không", null)
                .show();
    }
}