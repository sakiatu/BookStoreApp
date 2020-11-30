package com.beecoder.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddBooks extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "AddBooks";
    EditText text1,text2,text3,text4;
    Button btn;
    Spinner spinner;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    String[] category = {"Math","EEE","Physics","Data Structure","Algorithm","Story Books"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);
        text1=findViewById(R.id.edit_txt_title);
        text2=findViewById(R.id.edit_txt_author);
        text3=findViewById(R.id.edit_txt_edition);
        text4=findViewById(R.id.edit_txt_price);

        spinner=findViewById(R.id.spin_category);

        btn=findViewById(R.id.btn_add);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    public void Addingbooks(View view) {
       String bookName= text1.getText().toString();
       String authorName = text2.getText().toString();
       String edition= text3.getText().toString();
       String price= text4.getText().toString();
       String category = spinner.getSelectedItem().toString();

        Map<String,String> map = new HashMap<>();
        map.put("Title",bookName);
        map.put("Author",authorName);
        map.put("Edition",edition);
        map.put("Price",price);
        map.put("Category",category);

        firestore.collection("Books").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG,"Successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Failed");
                    }
                });

        startActivity(new Intent(AddBooks.this,Catalog.class));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}