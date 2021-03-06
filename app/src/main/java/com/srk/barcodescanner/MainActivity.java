package com.srk.barcodescanner;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    DatabaseReference db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkConnectivity()) {
            db = FirebaseDatabase.getInstance().getReference("RegisteredUsers");
        }
        //Insert Test Data
        /*pojo_UserDetails pjObj = new pojo_UserDetails("srk","srk0000@gmail.com","sjce","9443499270","1208","MALE",true);
        db.child(pjObj.id).setValue(pjObj);
        pojo_UserDetails pjObjTemp = new pojo_UserDetails("viver jio","monster@gmail.com","sjce","7373728393","2399","MALE",false);
        db.child(pjObjTemp.id).setValue(pjObjTemp);*/
    }
    public boolean checkConnectivity(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected = false;
        if(connectivityManager!=null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            isConnected = (networkInfo!=null) && (networkInfo.isConnectedOrConnecting());
        }
        if(!isConnected){
            Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION",Toast.LENGTH_LONG).show();
        }
        return isConnected;
    }
    public void searchUser(View view){
        if(checkConnectivity()) {
            EditText et = findViewById(R.id.uniqueId);
            final String unique = et.getText().toString();
            if (unique.length() != 0) {
                DatabaseReference dbObj = FirebaseDatabase.getInstance().getReference("RegisteredUsers");
                dbObj.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(unique).exists()) {
                            try {
                                pojo_UserDetails temp = new pojo_UserDetails();
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    temp = child.getValue(pojo_UserDetails.class);
                                    if (temp.id.equals(unique)) {
                                        break;
                                    }
                                }
                                if (temp.id.equals(unique)) {
                                    Intent intent = new Intent(getApplicationContext(), Selection.class);
                                    intent.putExtra("Object", temp);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (Exception e) {
                                Log.e("HERE1", e.getMessage());
                            }
                        } else {
                            DatabaseReference obj = FirebaseDatabase.getInstance().getReference("BarcodeMap");
                            obj.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(unique).exists()) {
                                        Toast.makeText(getApplicationContext(), "USER ALREADY REGISTERED", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "USER NOT REGISTERED", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                Toast.makeText(this, "Please enter a User ID".toUpperCase(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
