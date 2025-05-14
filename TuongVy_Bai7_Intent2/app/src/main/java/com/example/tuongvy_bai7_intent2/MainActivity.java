package com.example.tuongvy_bai7_intent2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnkq;
    EditText edta,edtb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        edta=findViewById(R.id.edta);
        edtb=findViewById(R.id.edtb);
        btnkq=findViewById(R.id.button);
        btnkq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(MainActivity.this, ketqua.class);
                Bundle bundle1=new Bundle();
                int a=Integer.parseInt(edta.getText()+"");
                int b=Integer.parseInt(edtb.getText()+"");
                // đẩy dl vào Bundle
                bundle1.putInt("soa",a);
                bundle1.putInt("sob",b);
                //đẩy bundle lên intent
                intent1.putExtra(("mybackage"),bundle1) ;
                startActivity(intent1);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}