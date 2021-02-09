package com.polinomyacademy.bookstore_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SellerActivity extends AppCompatActivity {
    private RecyclerView bookListView;
    private BookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        bookListView = findViewById(R.id.book_recyclerList);
    }

    private void initBookList() {
        Query query = FirebaseFirestore.getInstance().collection("Books")
                .whereEqualTo("approved", true)
                .whereEqualTo("sold", false);

        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();
        adapter = new BookAdapter(options, this);
        bookListView.setAdapter(adapter);
        adapter.setOnAddToCartClickListener(this::addToCart);
        adapter.startListening();
        CartDatabase cartDatabase = new CartDatabase();
        cartDatabase.getBookIds().addSnapshotListener((value, error) -> {
            if (!value.isEmpty()) {
                Carts.clearAll();
                for (DocumentSnapshot snapshot : value.getDocuments())
                    Carts.addBookId(snapshot.getId());
                adapter.notifyDataSetChanged();
            }
        });
    }
}