package com.example.dacn_vothituongvy_cr424b;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Màn thêm/sửa liên hệ
 */
public class AddEditContact extends AppCompatActivity {

    /* -------- hằng số requestCode -------- */
    private static final int CAM_PERM = 100;   // xin quyền camera
    private static final int GAL_PERM = 200;   // xin quyền đọc ảnh
    private static final int GAL_REQ  = 300;   // kết quả gallery
    private static final int CAM_REQ  = 400;   // kết quả camera

    /* -------- permission array -------- */
    private final String[] camPermission = { Manifest.permission.CAMERA };
    private String[]       galPermission;      // tuỳ API

    /* -------- view -------- */
    private ImageView profile;
    private EditText  edtName, edtPhone, edtEmail, edtNotes;

    /* -------- data -------- */
    private Uri   imageUri;      // Uri hiển thị
    private String imagePath=""; // Chuỗi lưu DB
    private DbHelper db;

    /* -------------------- onCreate -------------------- */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_contact);

        db = new DbHelper(this);

        /* quyền Gallery phụ thuộc phiên bản */
        galPermission = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                ? new String[]{ Manifest.permission.READ_MEDIA_IMAGES }
                : new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE };

        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setTitle("Add Contact");

        /* ánh xạ view */
        profile  = findViewById(R.id.imageViewProfile);
        edtName  = findViewById(R.id.name);
        edtEmail = findViewById(R.id.email);
        edtPhone = findViewById(R.id.phone);
        edtNotes = findViewById(R.id.notes);
        ImageButton btnAdd = findViewById(R.id.btnAdd);

        /* sự kiện */
        btnAdd.setOnClickListener(v -> saveData());
        profile.setOnClickListener(v -> chooseImage());
    }

    /* -------------------- chọn ảnh -------------------- */
    private void chooseImage() {
        String[] opt = { "Camera", "Gallery" };
        new AlertDialog.Builder(this)
                .setTitle("Choose an option")
                .setItems(opt, (d,w) -> {
                    if (w == 0) {                // Camera
                        if (checkCam()) pickCamera(); else reqCam();
                    } else {                     // Gallery
                        if (checkGal()) pickGallery(); else reqGal();
                    }
                }).show();
    }

    /* Gallery */
    private void pickGallery() {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, GAL_REQ);
    }

    /* Camera */
    private void pickCamera() {
        // /Android/data/<pkg>/files/Pictures/Contacts
        File dir = new File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "Contacts");
        if (!dir.exists()) dir.mkdirs();

        File f = new File(dir, "IMG_" + System.currentTimeMillis() + ".jpg");

        imageUri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".fileprovider",
                f);

        // lưu đường dẫn file tuyệt đối để ghi DB
        imagePath = f.getAbsolutePath();

        Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cam.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cam.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(cam, CAM_REQ);
    }

    /* -------------------- quyền -------------------- */
    private boolean checkCam() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    private void reqCam() {
        ActivityCompat.requestPermissions(this, camPermission, CAM_PERM);
    }

    private boolean checkGal() {
        return ContextCompat.checkSelfPermission(this,
                galPermission[0]) == PackageManager.PERMISSION_GRANTED;
    }
    private void reqGal() {
        ActivityCompat.requestPermissions(this, galPermission, GAL_PERM);
    }

    @Override
    public void onRequestPermissionsResult(int rc,
                                           @NonNull String[] p,
                                           @NonNull int[] g) {
        super.onRequestPermissionsResult(rc,p,g);
        if (rc == CAM_PERM && granted(g)) pickCamera();
        if (rc == GAL_PERM && granted(g)) pickGallery();
    }
    private boolean granted(int[] g){
        return g.length>0 && g[0]==PackageManager.PERMISSION_GRANTED;
    }

    /* ---------- kết quả ---------- */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;

        if (requestCode == GAL_REQ && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                imageUri = uri;
                imagePath = imageUri.toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final int takeFlags = data.getFlags() &
                            (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                    if (takeFlags != 0) {
                        try {

                            getContentResolver().takePersistableUriPermission(imageUri, takeFlags);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                            // Bỏ qua lỗi để tránh crash
                        }
                    }
                }
            }
        } else if (requestCode == CAM_REQ) {
            // Ảnh camera đã lưu đường dẫn imagePath rồi
            // Có thể xử lý thêm nếu cần
        }

        profile.setImageURI(imageUri);
    }



    /* -------------------- lưu DB -------------------- */
    private void saveData(){
        String name  = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String note  = edtNotes.getText().toString().trim();
        String ts    = String.valueOf(System.currentTimeMillis());

        if (name.isEmpty() && phone.isEmpty()
                && email.isEmpty() && note.isEmpty()){
            toast("Nothing to save"); return;
        }

        long id = db.insertContact(
                imagePath, name, phone, email, note, ts, ts);

        toast("Inserted id=" + id);
        finish(); // quay lại MainActivity
    }

    /* -------------------- tiện ích -------------------- */
    private void toast(String m){
        Toast.makeText(this,m,Toast.LENGTH_SHORT).show();
    }
}