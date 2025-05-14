package com.example.tuongvy_bai05_dangkythontincanhan;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtHoten,edtCMND,edtbosung;
    Button btnguitt;
    RadioGroup idgroup;
    CheckBox chkdocbao, chkdocsach, chkcoding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //Anh xa id
        edtHoten = findViewById(R.id.edtHoten);
        edtCMND = findViewById(R.id.edtCMND);
        edtbosung = findViewById(R.id.edtbosung);
        btnguitt = findViewById(R.id.btnguitt);
        idgroup = findViewById(R.id.idgroup);
        chkdocbao = findViewById(R.id.chkdocbao);
        chkcoding = findViewById(R.id.chkcoding);
        chkdocsach = findViewById(R.id.chkdocsach);
        //Xu ly click vao Button
        btnguitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lay thong tin ho ten
                String hoten = edtHoten.getText().toString();
                if  (hoten.length() <3)
                {
                    Toast.makeText(MainActivity.this, "Họ tên phải >= 3 ký tự", Toast.LENGTH_LONG).show();
                    edtHoten.requestFocus();
                    edtHoten.selectAll();
                    return;
                }
                //Lay thong tin CMND
                String CMND = edtCMND.getText().toString();
                if (CMND.length()!=9)
                {
                    Toast.makeText(MainActivity.this, "CMND phải đúng 9 số", Toast.LENGTH_LONG).show();
                    edtCMND.requestFocus();
                    edtCMND.selectAll();
                    return;
                }
                //Lay thong tin bang cap
                int idselect = idgroup.getCheckedRadioButtonId();
                RadioButton radselect = findViewById(idselect);
                String bangcap = radselect.getText().toString();
                //Lay thong tin so thich
                String sothich ="";
                if (chkdocsach.isChecked())
                    sothich += chkdocsach.getText().toString()+"-";
                if (chkcoding.isChecked())
                    sothich += chkcoding.getText().toString()+"-";
                if (chkdocbao.isChecked())
                    sothich += chkdocbao.getText().toString()+"-";
                //Thong tin bo sung
                String bosung = edtbosung.getText().toString();
                String tonghop = hoten+"\n"+CMND+"\n"+sothich+"\n";
                tonghop += "----------THÔNG TIN BỔ SUNG----------\n";
                tonghop +=bosung+"\n";
                tonghop += "---------------------------------------------";
                //Tao dialog va hien thi thong tin tong hop len
                AlertDialog.Builder mydialog = new AlertDialog.Builder(MainActivity.this);
                mydialog.setTitle("THÔNG TIN CÁ NHÂN");
                mydialog.setMessage(tonghop);
                mydialog.setPositiveButton("ĐÓNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                });
                mydialog.create().show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}