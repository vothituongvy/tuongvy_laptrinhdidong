package com.example.vothituongvy_bai2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    EditText edtCustomerName, edtQuantity, edtTotal, edt1, edt2, edt3;
    CheckBox cbVip;
    Button btnCalculate, btnNext, btnStatistics;
    ImageButton btnExit;

    private int totalCustomers = 0;
    private int totalVipCustomers = 0;
    private double totalRevenue = 0;
    private final double BOOK_PRICE = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtTotal = findViewById(R.id.edtTotal);
        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        edt3 = findViewById(R.id.edt3);
        cbVip = findViewById(R.id.cbVip);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnNext = findViewById(R.id.btnNext);
        btnStatistics = findViewById(R.id.btnStatistics);
        btnExit = findViewById(R.id.btnExit);

        // Gán sự kiện
        btnCalculate.setOnClickListener(v -> calculateAndSave());
        btnNext.setOnClickListener(v -> nextCustomer());
        btnStatistics.setOnClickListener(v -> showStatistics());
        btnExit.setOnClickListener(v -> showExitDialog());
    }

    // Cách 2: Gộp xử lý tính và lưu doanh thu tại đây
    private void calculateAndSave() {
        try {
            int quantity = Integer.parseInt(edtQuantity.getText().toString().trim());

            if (quantity <= 0) {
                edtTotal.setText("Số lượng phải > 0");
                return;
            }

            double total = quantity * BOOK_PRICE;
            if (cbVip.isChecked()) {
                total *= 0.9;
                totalVipCustomers++;
            }

            // Cập nhật doanh thu, khách hàng
            totalRevenue += total;
            totalCustomers++;

            // Hiển thị thành tiền
            edtTotal.setText(String.format("%,.0f VND", total));

        } catch (NumberFormatException e) {
            edtTotal.setText("Vui lòng nhập số hợp lệ");
        }
    }

    private void nextCustomer() {
        // Xoá thông tin nhập liệu để tiếp khách mới
        edtCustomerName.setText("");
        edtQuantity.setText("");
        edtTotal.setText("");
        cbVip.setChecked(false);
        edtCustomerName.requestFocus();
    }

    private void showStatistics() {
        edt1.setText(String.valueOf(totalCustomers));
        edt2.setText(String.valueOf(totalVipCustomers));
        edt3.setText(String.format("%,.0f VND", totalRevenue));
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
