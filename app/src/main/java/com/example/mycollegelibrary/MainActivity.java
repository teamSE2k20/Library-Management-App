package com.example.mycollegelibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    String ScannedData;// the scanned value storing string
    Button Add,scan, database; //buttons in the main activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //scan button
        scan = (Button)findViewById(R.id.scanBtn);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanIssueCode();
            }
        });
        //add database button
        Add = (Button) findViewById(R.id.addBtn);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBookEntry();
            }
        });
        //button to check books database
        database = (Button)findViewById(R.id.dbBtn);
        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBookDatabase();
            }
        });

    }

    private void scanIssueCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    } // for scanning code

    @Override//for retrieveing scanned value
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            ScannedData = result.getContents();
            if (ScannedData != null){
                /*AlertDialog.Builder builder =  new AlertDialog.Builder(this);
                builder.setMessage(ScannedData);
                builder.setTitle("Issue Value");
                builder.setPositiveButton("Search Database", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {*/
                        openScanResult();
                   // }
                //});
                //AlertDialog dialog = builder.create();
               // dialog.show();

            }
            else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }

        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }





    public void openScanResult(){

        Intent intent = new Intent(this, ScanResult.class);
        intent.putExtra(EXTRA_TEXT,ScannedData);
        startActivity(intent);

    } // for opening a new activity including scanned value

    public void openBookEntry(){
        Intent intent = new Intent(this, BookEntry.class);
        startActivity(intent);
    }// for adding books

    public void openBookDatabase(){

        Intent intent = new Intent(this, BookDatabase.class);
        intent.putExtra(EXTRA_TEXT,ScannedData);
        startActivity(intent);
        
    }
}
