package com.example.dacn_vothituongvy_cr424b;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private DbHelper db;
    private AdapterContact adapter;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        db = new DbHelper(this);

        rv = findViewById(R.id.contactRv);
        rv.setHasFixedSize(true);

        ImageButton plus = findViewById(R.id.btnAdd);
        plus.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditContact.class)));

        loadData();
    }

    private void loadData() {
        adapter = new AdapterContact(this, db.getAllData());
        adapter.setOnItemClickListener(contact -> {
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
            intent.putExtra("CONTACT_ID", contact.getId());
            startActivity(intent);
        });
        rv.setAdapter(adapter);
    }


    @Override protected void onResume() {
        super.onResume();
        loadData();
    }
}
