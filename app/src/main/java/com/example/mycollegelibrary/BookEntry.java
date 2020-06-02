package com.example.mycollegelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookEntry extends AppCompatActivity {
    EditText Barcode,BookName,Author;
    Button submit;
    DatabaseReference ref;
    LibData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_entry);
        Barcode = (EditText) findViewById(R.id.Bcode);
        BookName = (EditText) findViewById(R.id.Bname);
        Author = (EditText) findViewById(R.id.Bathr);
        submit = (Button) findViewById(R.id.submit);
        user = new LibData();
        ref = FirebaseDatabase.getInstance().getReference().child("LibData");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Barcode.length()==0){
                    Barcode.setError("Enter Barcode");
                }else if(BookName.length()==0){
                    BookName.setError("Enter Book Name");
                }else if(Author.length()==0){
                    Author.setError("Enter Author name");
                } else {
                    user.setBarcode(Barcode.getText().toString().trim());
                    user.setBookName(BookName.getText().toString().trim());
                    user.setAuthor(Author.getText().toString().trim());
                    ref.push().setValue(user);
                    Toast.makeText(BookEntry.this, "data inserted successfully!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
