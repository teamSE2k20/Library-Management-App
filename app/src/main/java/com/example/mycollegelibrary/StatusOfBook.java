package com.example.mycollegelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StatusOfBook extends AppCompatActivity implements sidDialog.sidDialogListener{
    RadioGroup radioGroup;
    RadioButton radioButton;
    private ListView viewStatus;
    String studentid;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_of_book);
        // radiogrp
        radioGroup = findViewById(R.id.radio);
        // for getting the value of clicked book in the list
        Intent secondIntent = getIntent();
        String message = secondIntent.getStringExtra("book_selected");
        final String ScannedData = secondIntent.getStringExtra(ScanResult.EXTRA_TEXT2);
        TextView myText = (TextView) findViewById(R.id.bla);
        myText.setText(message);
        //checkout button
        Button btnCheckout = findViewById(R.id.checkout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                String radioValue = radioButton.getText().toString();
                radioButton = findViewById(radioId);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("BookData")
                        .child(ScannedData);

                if (radioValue.equals("submit") == true){
                    ref.child("status").setValue(radioValue+"ed back to library");
                    ref.child("barcode").setValue(ScannedData);
                    Toast.makeText(StatusOfBook.this,"updated successfully!",Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(StatusOfBook.this, MainActivity.class);
                    startActivity(in);
                }else if (studentid.length()==0){
                    openDialog();
                }
                else{
                    ref.child("status").setValue(radioValue +"d to student ID " + studentid);
                    ref.child("barcode").setValue(ScannedData);
                    Toast.makeText(StatusOfBook.this,"updated successfully!",Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(StatusOfBook.this, MainActivity.class);
                    startActivity(in);
                }

            }
        });

        //view Status of book
        viewStatus = findViewById(R.id.Statusview);
        final ArrayList<String> listS = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, listS);
        viewStatus.setAdapter(adapter);

        // to show database of bookstatus
        Query ref = FirebaseDatabase.getInstance().getReference().child("BookData")
                .orderByChild("barcode").equalTo(ScannedData);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listS.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    listS.add(snapshot.getValue(StatusView.class).toString());

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void openDialog() {
        sidDialog dialog = new sidDialog();
        dialog.show(getSupportFragmentManager(),"sid Dialog ");
    }

    @Override
    public void applytexts(String studentID) {
        studentid = studentID;
    }

    //for the output of radio button
    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "Selected: " + radioButton.getText(),Toast.LENGTH_SHORT).show();
        if (radioButton.getText().equals("issue")==true){
            openDialog();
        }
    }
}
