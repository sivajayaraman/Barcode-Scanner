package com.srk.barcodescanner;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference db ;
    public String barCodeValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=FirebaseDatabase.getInstance().getReference("RegisteredUsers");

        //Insert Test Data

        pojo_UserDetails pjObj = new pojo_UserDetails("srk","sjce@gmail.com","sjce","789","1213",true);
        db.child(pjObj.uniqueId).setValue(pjObj);
    }

    public void searchUser(View view){
        EditText et=findViewById(R.id.uniqueId);
        final String uniqueId=et.getText().toString();
        if(uniqueId.length()!=0){
            DatabaseReference temp = FirebaseDatabase.getInstance().getReference("RegisteredUsers");
            temp.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(uniqueId).exists()){
                        Intent intent = new Intent(getApplicationContext(),scanner.class);
                        intent.putExtra("UniqueId",uniqueId);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "USER NOT REGISTERED!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            Toast.makeText(this, "Please enter a User ID", Toast.LENGTH_SHORT).show();
        }
    }

}
