package com.beecoder.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beecoder.bookstore.Authentication.AuthActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.profile_layout, container, false);
        Button signOut = layout.findViewById(R.id.signOut);
        TextView email = layout.findViewById(R.id.email);
        TextView username = layout.findViewById(R.id.username);

        email.setText(user.getEmail());
        username.setText(user.getDisplayName());

        signOut.setOnClickListener(v -> logout());
        return layout;
    }

    private void logout() {
        AuthUI.getInstance().signOut(getActivity());
        startActivity(new Intent(getActivity(), AuthActivity.class));
        getActivity().finish();
    }
}
