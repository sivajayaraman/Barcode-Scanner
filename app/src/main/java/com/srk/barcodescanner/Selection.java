package com.srk.barcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Selection extends AppCompatActivity {

    public String barcodeValue;
    pojo_UserDetails details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        try{
            details = (pojo_UserDetails) getIntent().getSerializableExtra("Object");
        }
        catch (Exception e){
            Log.e("HERE",e.getMessage());
        }
    }
    public void scanBarcode(View view){
        Intent intent = new Intent(this,scanner.class);
        intent.putExtra("Object",details);
        startActivity(intent);
        finish();
    }
    public void enterBarcode(View view){
        EditText barcodeValueEd = findViewById(R.id.barcodevalueStr);
        try {
            barcodeValue = barcodeValueEd.getText().toString();
            details.b = this.barcodeValue;
            Intent intent = new Intent(this, barCodeScanner.class);
            intent.putExtra("Object", details);
            startActivity(intent);
            finish();
        }
        catch (Exception E){
            Log.e("Barcode Value Error",E.getMessage());
        }
    }
}
