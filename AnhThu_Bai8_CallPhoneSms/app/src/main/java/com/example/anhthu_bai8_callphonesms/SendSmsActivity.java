package com.example.anhthu_bai8_callphonesms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SendSmsActivity extends AppCompatActivity {
EditText edt_sms;
Button btn_back2;
ImageButton btn_sms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_sms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_sms = findViewById(R.id.btn_sms);
        edt_sms = findViewById(R.id.edt_sms);
        btn_back2 = findViewById(R.id.btn_back2);
        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callintent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+edt_sms.getText().toString()));
                startActivity(callintent);
            }
        });
    btn_back2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
            }
        });
    }
}