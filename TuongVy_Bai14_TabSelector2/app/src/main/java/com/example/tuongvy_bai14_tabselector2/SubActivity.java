package com.example.tuongvy_bai14_tabselector2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    TextView txtmaso,txtbaihat,txtloibaihat,txttacgia;
    ImageButton btnthich;
    int trangthai =  0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        txtmaso = findViewById(R.id.txtmaso1);
        txtbaihat = findViewById(R.id.txtbaihat);
        txtloibaihat = findViewById(R.id.txtloibaihat);
        txttacgia = findViewById(R.id.txttacgia);
        btnthich = findViewById(R.id.btnlikesub);
//Nhận Intent từ myarrayAdapter, lấy dữ liệu khỏi Bundle
        Intent callerIntent1 = getIntent();
        Bundle backagecaller1 = callerIntent1.getBundleExtra("package");
        String maso = backagecaller1.getString("maso");
//Truy vấn dữ liệu từ maso nhận được; Hiển thị dữ liệu Mã bài hát, Tên bài hát, Lời bài
//hát, Tác giả, Trạng thái Thích lên SubActivity
        Cursor c = MainActivity.database.rawQuery("SELECT * FROM ArirangSongList WHERE MABH LIKE'"+maso+"'", null);
        txtmaso.setText(maso);
        c.moveToFirst();
        txtbaihat.setText(c.getString(2));
        txtloibaihat.setText(c.getString(3));
        txttacgia.setText(c.getString(4));
        trangthai = c.getInt(6);
        if (trangthai  ==0) {
            btnthich.setImageResource(R.drawable.unlike);
        } else {
            btnthich.setImageResource(R.drawable.love);
        }
        c.close();
        //Xử lý sự kiện khi click vào Button btnthich
        //Cập nhật dữ liệu vào CSDL, thay đổi trạng thái hiển thị cho Button btnthich
        btnthich.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                if (trangthai ==0) {
                    trangthai = 1;
                    btnthich.setImageResource(R.drawable.love);
                } else {
                    trangthai = 0;
                    btnthich.setImageResource(R.drawable.unlike);
                }
                values.put("YEUTHICH", trangthai);
                MainActivity.database.update("ArirangSongList", values,
                        "MABH=?", new String[]{txtmaso.getText().toString()});
            }
        });
    }
}
