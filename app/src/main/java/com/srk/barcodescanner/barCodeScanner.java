package com.srk.barcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class barCodeScanner extends AppCompatActivity {
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_scanner);
        Intent intent = getIntent();
        String barCodeValue = intent.getStringExtra("barCodeValue");
        String uniqueId = intent.getStringExtra("uniqueId");
    }
}