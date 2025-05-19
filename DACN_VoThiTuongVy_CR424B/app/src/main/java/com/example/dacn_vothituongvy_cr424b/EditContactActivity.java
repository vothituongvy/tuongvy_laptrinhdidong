package com.example.dacn_vothituongvy_cr424b;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditContactActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1001;
    private static final int REQUEST_IMAGE_CAMERA = 1002;
    private static final int REQUEST_CAMERA_PERMISSION = 2001;

    private EditText nameET, phoneET, emailET, noteET;
    private ImageView imageView;
    private ImageButton btnUpdate;
    private DbHelper db;
    private String contactId;
    private ModelContact contact;
    private String selectedImageUri = null;
    private Uri cameraUri;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        nameET = findViewById(R.id.name);
        phoneET = findViewById(R.id.phone);
        emailET = findViewById(R.id.email);
        noteET = findViewById(R.id.notes);
        imageView = findViewById(R.id.imageViewProfile);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = new DbHelper(this);
        contactId = getIntent().getStringExtra("CONTACT_ID");
        contact = db.getContactById(contactId);

        if (contact != null) {
            nameET.setText(contact.getName());
            phoneET.setText(contact.getPhone());
            emailET.setText(contact.getEmail());
            noteET.setText(contact.getNote());

            String img = contact.getImage();
            if (img != null && !img.isEmpty()) {
                if (img.startsWith("/")) {
                    imageView.setImageURI(Uri.fromFile(new File(img)));
                } else {
                    imageView.setImageURI(Uri.parse(img));
                }
            } else {
                imageView.setImageResource(R.drawable.avatar);
            }
        }

        imageView.setOnClickListener(v -> chooseImage());
        btnUpdate.setOnClickListener(v -> showUpdateConfirmationDialog());
    }

    private void chooseImage() {
        String[] options = {"Camera", "Gallery"};
        new AlertDialog.Builder(this)
                .setTitle("Choose an option")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_GRANTED) {
                            openCamera();
                        } else {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                        }
                    } else {
                        openGallery();
                    }
                }).show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    private void openCamera() {
        File dir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Contacts");
        if (!dir.exists()) dir.mkdirs();

        File file = new File(dir, "IMG_" + System.currentTimeMillis() + ".jpg");
        cameraUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
        imagePath = file.getAbsolutePath();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_IMAGE_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == REQUEST_IMAGE_PICK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            selectedImageUri = imageUri.toString();
            imageView.setImageURI(imageUri);
        } else if (requestCode == REQUEST_IMAGE_CAMERA) {
            selectedImageUri = imagePath;
            imageView.setImageURI(Uri.fromFile(new File(imagePath)));
        }
    }

    private void showUpdateConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận cập nhật liên hệ")
                .setMessage("Bạn có chắc chắn muốn cập nhật lại liên hệ này không ?")
                .setPositiveButton("Có", (dialog, which) -> updateContact())
                .setNegativeButton("Không", null)
                .show();
    }

    private void updateContact() {
        String name = nameET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String note = noteET.getText().toString().trim();

        String updatedTime = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(new Date());
        String imageToSave = (selectedImageUri != null) ? selectedImageUri : contact.getImage();

        db.updateContact(
                contactId,
                imageToSave,
                name,
                phone,
                email,
                note,
                contact.getAddedTime(),
                updatedTime
        );

        Toast.makeText(this, "Contact updated", Toast.LENGTH_SHORT).show();
        finish();
    }
}
