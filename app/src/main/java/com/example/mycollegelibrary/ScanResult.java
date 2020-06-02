package com.example.mycollegelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ScanResult extends AppCompatActivity {
    public static final String EXTRA_TEXT2 = "com.example.application.example.EXTRA_TEXT2";
    private ListView listview;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        // bringing scanned value to this activity
        Intent intent = getIntent();
        final String ScannedData = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        TextView textView1 = findViewById(R.id.view1);
        textView1.setText(ScannedData);

        // list view for viewing data
        listview = findViewById(R.id.listview);
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, list);
        listview.setAdapter(adapter);

        // to search data of scanned book
        search = findViewById(R.id.searchBtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference().child("LibData")
                        .orderByChild("barcode").equalTo(ScannedData);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            list.add(snapshot.getValue(LibData.class).toString());
                        }
                        if(list.size()== 0){
                            Toast.makeText(ScanResult.this, "this is not a college book", Toast.LENGTH_LONG).show();
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        });
        // to show whole database
        /**/
        // for clicking a value from the list
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) listview.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), StatusOfBook.class);
                intent.putExtra("book_selected", itemValue);
                intent.putExtra(EXTRA_TEXT2,ScannedData);
                startActivity(intent);
            }
        });


    }

}
