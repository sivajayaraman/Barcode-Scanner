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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=FirebaseDatabase.getInstance().getReference("RegisteredUsers");

        //Insert Test Data

        //pojo_UserDetails pjObj = new pojo_UserDetails("srk","sjce@gmail.com","sjce","789","123",true);
        //db.child(pjObj.uniqueId).setValue(pjObj);
        //pojo_UserDetails pjObjTemp = new pojo_UserDetails("pravee","lifeline@gmail.com","velamal","11212","1234",false);
        //db.child(pjObjTemp.uniqueId).setValue(pjObjTemp);
    }

    public void searchUser(View view){
        EditText et=findViewById(R.id.uniqueId);
        final String unique=et.getText().toString();
        if(unique.length()!=0){
            DatabaseReference temp = FirebaseDatabase.getInstance().getReference("RegisteredUsers");
            temp.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(unique).exists()){
                        try {
                            pojo_UserDetails temp = new pojo_UserDetails();
                            for(DataSnapshot child : dataSnapshot.getChildren()){
                                temp = child.getValue(pojo_UserDetails.class);
                                if(temp.uniqueId.equals(unique) && !temp.registered)
                                {
                                    break;
                                }
                            }
                            if(temp.registered){
                                Toast.makeText(getApplicationContext(),"USER REGISTERED ALREADY",Toast.LENGTH_SHORT).show();
                            }
                            else if(temp.uniqueId.equals(unique)){
                                Intent intent = new Intent(getApplicationContext(), scanner.class);
                                intent.putExtra("Object", temp);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "USER REGISTERED ALREADY", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Log.e("HERE",e.getMessage());
                        }

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
            Toast.makeText(this, "Please enter a User ID".toUpperCase(), Toast.LENGTH_SHORT).show();
        }
    }

}
