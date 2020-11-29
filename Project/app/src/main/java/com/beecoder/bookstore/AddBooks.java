package com.beecoder.bookstore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddBooks extends AppCompatActivity {
    private static final String TAG = "AddBooks";
    EditText text1,text2,text3,text4;
    Button btn;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);
        text1=findViewById(R.id.edit_txt_title);
        text2=findViewById(R.id.edit_txt_author);
        text3=findViewById(R.id.edit_txt_edition);
        text4=findViewById(R.id.edit_txt_price);

        btn=findViewById(R.id.btn_add);
    }

    public void Addingbooks(View view) {
       String bookName= text1.getText().toString();
       String authorName = text2.getText().toString();
       String edition= text3.getText().toString();
       String price= text4.getText().toString();

        Map<String,String> map = new HashMap<>();
        map.put("Title",bookName);
        map.put("Author",authorName);
        map.put("Edition",edition);
        map.put("Price",price);

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

    }
}