package com.beecoder.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CatalogueFragment extends Fragment {
    private RecyclerView categoryListView;

    private CategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.catalogue_layout, container, false);
        categoryListView = layout.findViewById(R.id.category_recyclerList);

        return layout;
    }

    private void initCategoryList() {
        Query query = FirebaseFirestore.getInstance()
                .collection("Category");

        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();

        adapter = new CategoryAdapter(options);
        categoryListView.setAdapter(adapter);
        adapter.setOnItemClickListener(this::openCategoryItemActivity);
        adapter.startListening();
    }

    private void openCategoryItemActivity(DocumentSnapshot snapshot) {
        Intent intent = new Intent(getActivity(), CategoryItemActivity.class);
        intent.putExtra("category", snapshot.toObject(Category.class).getName());
        startActivity(intent);
    }

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

    private FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
        if (firebaseAuth.getCurrentUser() != null) initCategoryList();
    };
}
