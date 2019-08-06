package com.srk.barcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class barCodeScanner extends AppCompatActivity {
    DatabaseReference db;
    pojo_UserDetails details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_scanner);
        details = (pojo_UserDetails) getIntent().getSerializableExtra("Object");
        TextView tv = findViewById(R.id.uniqueIdDisplay);
        tv.setText(details.uniqueId);
        tv = findViewById(R.id.name);
        tv.setText(details.userName);
        tv = findViewById(R.id.phone);
        tv.setText(details.phoneNumber);
        tv = findViewById(R.id.email);
        tv.setText(details.emailId);
        tv = findViewById(R.id.college);
        tv.setText(details.collegeName);
        tv = findViewById(R.id.veg);
        if(details.veg) {
            tv.setText("VEG");
        }
        else {
            tv.setText("NON VEG");
        }
        tv = findViewById(R.id.barcodeValue);
        tv.setText(details.barCodeValue);
    }
    public void registerUser(View view){
        db = FirebaseDatabase.getInstance().getReference("BarcodeMap");
        db.child(details.barCodeValue).setValue(details.uniqueId);
        details.registered=true;
        db = FirebaseDatabase.getInstance().getReference("RegisteredUsers").child(details.uniqueId);
        db.child("registered").setValue(true);
        Toast.makeText(this,"REGISTRATION SUCCESSFUL",Toast.LENGTH_LONG).show();
        Intent startIntent = new Intent(this, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(startIntent);
        finish();
    }
}