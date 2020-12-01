package com.beecoder.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Catalog extends AppCompatActivity {

    Button button1,button2,button3,button4,button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        button1=findViewById(R.id.btn_math);
        button2=findViewById(R.id.btn_EEE);
        button3=findViewById(R.id.btn_Physics);
        button4=findViewById(R.id.btn_DataStructure);
        button5=findViewById(R.id.btn_storyBooks);
    }

    public void MathSection(View view) {

        startActivity(new Intent(this,MathBooks.class));
    }

    public void EEEsection(View view) {
    }

    public void PhysicsSection(View view) {
    }

    public void DSsection(View view) {
    }

    public void StoryBooksection(View view) {
    }
}