package com.beecoder.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.home_layout, container, false);
        FloatingActionButton fab = layout.findViewById(R.id.fab);
        fab.setOnClickListener(v -> openAddBookActivity());
        return layout;
    }

    private void openAddBookActivity() {
        Intent intent = new Intent(getActivity(), AddBookActivity.class);
        startActivity(intent);
    }
}
