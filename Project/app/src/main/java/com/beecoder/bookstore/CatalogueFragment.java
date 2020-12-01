package com.beecoder.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CatalogueFragment extends Fragment {
    private Button math, button2, button3, button4, button5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.catalogue_layout, container, false);
        math = layout.findViewById(R.id.btn_math);
        button2 = layout.findViewById(R.id.btn_EEE);
        button3 = layout.findViewById(R.id.btn_Physics);
        button4 = layout.findViewById(R.id.btn_DataStructure);
        button5 = layout.findViewById(R.id.btn_storyBooks);
        math.setOnClickListener(this::mathSection);
        return layout;
    }


    public void mathSection(View view) {
        startActivity(new Intent(getActivity(), MathCatelogActivity.class));
    }

    public void eeeSection(View view) {
    }

    public void physicsSection(View view) {
    }

    public void dsSection(View view) {
    }

    public void storyBookSection(View view) {
    }
}
