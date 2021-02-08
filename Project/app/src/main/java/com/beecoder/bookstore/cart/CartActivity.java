package com.beecoder.bookstore.cart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.beecoder.bookstore.Book;
import com.beecoder.bookstore.CartBookAdapter;
import com.beecoder.bookstore.R;
import com.beecoder.bookstore.database.CartDatabase;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class CartActivity extends AppCompatActivity {

    private RecyclerView bookListView;
    private CartBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

    }

    private void initBookList() {
        Query query = new CartDatabase().getBookIds();

        bookListView = findViewById(R.id.book_recyclerList);
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();
        adapter = new CartBookAdapter(options, this);
        adapter.setOnCancelClickListener(this::removeFromCart);
        bookListView.setAdapter(adapter);
        adapter.startListening();
    }

    private void removeFromCart(DocumentSnapshot snapshot,int position) {
        new CartDatabase().removeBook(snapshot.getId())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        Carts.removeBookId(snapshot.getId());
                    adapter.notifyItemRemoved(position);
                });
    }

    private FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
        if (firebaseAuth.getCurrentUser() != null)
            initBookList();
    };

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null)
            FirebaseAuth.getInstance().addAuthStateListener(authStateListener);

        if (adapter != null)
            adapter.stopListening();
    }

}