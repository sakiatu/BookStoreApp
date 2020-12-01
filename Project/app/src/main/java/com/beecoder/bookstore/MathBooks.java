package com.beecoder.bookstore;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MathBooks extends AppCompatActivity {

    RecyclerView recyclerView;
    BookRecylcerViewAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_books);

        recyclerView=findViewById(R.id.book_recyclerView);

        Query query=FirebaseFirestore.getInstance()
                .collection("Books")
                .whereEqualTo("Category","Story Book");
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query,Book.class).build();
        bookAdapter=new BookRecylcerViewAdapter(options);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.startListening();

    }

    /*public void initRecycler()
    {
        Query query=FirebaseFirestore.getInstance()
                .collection("Books")
                .whereEqualTo("Category","Story Book");
        FirestoreRecyclerOptions<Book> options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query,Book.class).build();
        bookAdapter=new BookRecylcerViewAdapter(options);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.startListening();
    }*/
}