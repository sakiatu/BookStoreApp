package com.beecoder.bookstore.fragmentsMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.beecoder.bookstore.Book;
import com.beecoder.bookstore.CartBookAdapter;
import com.beecoder.bookstore.R;
import com.beecoder.bookstore.cart.Carts;
import com.beecoder.bookstore.database.CartDatabase;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class CartFragment extends Fragment {

    private RecyclerView bookListView;
    private CartBookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        bookListView = layout.findViewById(R.id.book_recyclerList);
        return layout;
    }

    private void initBookList() {
        Query query = new CartDatabase().getBookIds();


        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Book>()
                .setQuery(query, Book.class)
                .build();
        adapter = new CartBookAdapter(options, getActivity());
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