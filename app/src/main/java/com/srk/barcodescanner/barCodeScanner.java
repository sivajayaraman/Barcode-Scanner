package com.srk.barcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class barCodeScanner extends AppCompatActivity {
    DatabaseReference db;
    pojo_UserDetails details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_scanner);
        details = (pojo_UserDetails) getIntent().getSerializableExtra("Object");
        TextView tv = findViewById(R.id.uniqueIdDisplay);
        tv.setText(details.id);
        tv = findViewById(R.id.name);
        tv.setText(details.u);
        tv = findViewById(R.id.phone);
        tv.setText(details.p);
        tv = findViewById(R.id.email);
        tv.setText(details.e);
        tv = findViewById(R.id.college);
        tv.setText(details.c);
        tv = findViewById(R.id.gender);
        tv.setText(details.g);
        tv = findViewById(R.id.reg);
        tv.setText(details.n);
        tv = findViewById(R.id.veg);
        if(details.v) {
            tv.setText("VEG");
        }
        else {
            tv.setText("NON VEG");
        }
        tv = findViewById(R.id.barcodeValue);
        tv.setText(details.b);
    }
    public void registerUser(View view){
        /*
        db = FirebaseDatabase.getInstance().getReference("RegisteredUsers");
        db.child(details.b).setValue(details.id);
        details.r=true;
        db = FirebaseDatabase.getInstance().getReference("RegisteredUsers").child(details.id);
        db.child("r").setValue(true);
         */
        db = FirebaseDatabase.getInstance().getReference("RegisteredUsers");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(details.b).exists()){
                    Toast.makeText(barCodeScanner.this, "ALREADY ASSINGED BARCODE VALUE!", Toast.LENGTH_LONG).show();
                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(startIntent);
                    finish();
                }
                else{
                    details.r=true;
                    db = FirebaseDatabase.getInstance().getReference("RegisteredUsers");
                    db.child(details.id).removeValue();
                    db.child(details.b).setValue(details);
                    db = FirebaseDatabase.getInstance().getReference("BarcodeMap");
                    db.child(details.id).setValue(details.b);
                    Toast.makeText(getApplicationContext(),"REGISTRATION SUCCESSFUL",Toast.LENGTH_LONG).show();
                    Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(startIntent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}