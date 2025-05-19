package com.example.dacn_vothituongvy_cr424b;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class ContactDetailActivity extends AppCompatActivity {

    private TextView nameTextView, phoneTextView, emailTextView, noteTextView, addedTextView, updatedTextView;
    private ImageView imageView;
    private DbHelper db;
    private ModelContact contact; // Lưu đối tượng contact để dùng khi xóa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);

        db = new DbHelper(this);

        String contactId = getIntent().getStringExtra("CONTACT_ID");
        contact = db.getContactById(contactId);

        nameTextView = findViewById(R.id.nameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        emailTextView = findViewById(R.id.emailTextView);
        noteTextView = findViewById(R.id.noteTextView);
        addedTextView = findViewById(R.id.addedTextView);
        updatedTextView = findViewById(R.id.updatedTextView);
        imageView = findViewById(R.id.imageView);

        ImageButton deleteButton = findViewById(R.id.deleteButton);
        ImageButton editButton = findViewById(R.id.editButton);

// Khi click vào nút edit → chuyển sang EditContactActivity và truyền ID
        editButton.setOnClickListener(v -> {
            if (contact != null) {
                Intent intent = new Intent(ContactDetailActivity.this, EditContactActivity.class);
                intent.putExtra("CONTACT_ID", contact.getId()); // truyền ID
                startActivity(intent);
            } else {
                Toast.makeText(this, "Không thể chỉnh sửa. Liên hệ không tồn tại.", Toast.LENGTH_SHORT).show();
            }
        });


        if (contact != null) {
            nameTextView.setText(contact.getName());
            phoneTextView.setText("Phone: " + contact.getPhone());
            emailTextView.setText("Email: " + contact.getEmail());
            noteTextView.setText(contact.getNote());
            addedTextView.setText("Added Time: " + contact.getAddedTime());
            updatedTextView.setText("Updated Time: " + contact.getUpdatedTime());

            String img = contact.getImage();
            if (img != null && !img.isEmpty()) {
                if (img.startsWith("/")) {
                    imageView.setImageURI(android.net.Uri.fromFile(new File(img)));
                } else {
                    imageView.setImageURI(android.net.Uri.parse(img));
                }
            } else {
                imageView.setImageResource(R.drawable.avatar);
            }
        }

        // Xử lý sự kiện xóa
        deleteButton.setOnClickListener(view -> showDeleteConfirmationDialog());
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa liên hệ này?")
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    if (contact != null) {
                        db.deleteContact(contact.getId());
                        Toast.makeText(this, "Đã xóa liên hệ", Toast.LENGTH_SHORT).show();
                        finish(); // Đóng màn hình chi tiết
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }
}
