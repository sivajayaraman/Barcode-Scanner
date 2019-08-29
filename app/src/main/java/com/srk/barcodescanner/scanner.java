package com.srk.barcodescanner;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;


public class scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public static TextView tv1, tv2;
    ZXingScannerView camScanner;
    public String barcodeValue;
    pojo_UserDetails details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        tv1 = findViewById(R.id.id_tv_userNo);
        tv2 = findViewById(R.id.id_tv_barcodeValue);
        try{
            details = (pojo_UserDetails) getIntent().getSerializableExtra("Object");
        }
        catch (Exception e){
            Log.e("HERE",e.getMessage());
        }
        camScanner = new ZXingScannerView(this);
        setContentView(camScanner);
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
            barcodeValue=rawResult.getText();
            details.b=this.barcodeValue;
            Intent intent = new Intent(this,barCodeScanner.class);
            intent.putExtra("Object",details);
            startActivity(intent);
            finish();
        }
        catch (Exception e){
            Log.e("HERE",e.getMessage());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        camScanner.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        camScanner.setResultHandler(this);
        camScanner.startCamera();
    }
}