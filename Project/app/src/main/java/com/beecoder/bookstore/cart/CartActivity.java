package com.beecoder.bookstore.cart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.beecoder.bookstore.Book;
import com.beecoder.bookstore.BookAdapter;
import com.beecoder.bookstore.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }
}