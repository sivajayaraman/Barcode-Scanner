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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        tv1 = findViewById(R.id.id_tv_userNo);
        tv2 = findViewById(R.id.id_tv_barcodeValue);
        camScanner = new ZXingScannerView(this);
        setContentView(camScanner);
        Intent intent = getIntent();
        String uniqueId=intent.getStringExtra("UniqueId");
        tv1.setText(uniqueId);
    }

    @Override
    public void handleResult(Result rawResult) {
        barcodeValue=rawResult.getText();
        Intent intent = new Intent(this,barCodeScanner.class);
        String uniqueId=tv1.getText().toString();
        intent.putExtra("uniqueId",uniqueId);
        intent.putExtra("barCodeValue",barcodeValue);
        startActivity(intent);
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