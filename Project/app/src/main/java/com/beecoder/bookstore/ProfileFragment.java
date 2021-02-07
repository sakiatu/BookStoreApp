package com.beecoder.bookstore;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private TextView username, phoneNumber_tv;
    private ImageView editPhone, imgBtn_photoEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.profile_layout, container, false);
        setViews(layout);
        return layout;
    }

    private void setViews(View layout) {
        TextView email = layout.findViewById(R.id.email);
        phoneNumber_tv = layout.findViewById(R.id.phone_number);
        username = layout.findViewById(R.id.username);
        editPhone = layout.findViewById(R.id.edit_phone_number);
        imgBtn_photoEdit = layout.findViewById(R.id.imgBtn_photoEdit);
        editPhone.setOnClickListener(view -> showEditPhoneDialogue());
        if (CurrentUser.getCurrentUser() != null) {
            email.setText(CurrentUser.getCurrentUser().getEmail());
            username.setText(CurrentUser.getCurrentUser().getName());
            phoneNumber_tv.setText(CurrentUser.getCurrentUser().getPhoneNumber());
        }
        imgBtn_photoEdit.setOnClickListener(v -> openGallery());

    }

    private void openGallery() {
        //open gallery
        //choose photo
        //size komabi
        //upload photo to storage and get url
        // set url(User.setPhotoUrl(url))
    }

    private void showEditPhoneDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        EditText editText = new EditText(getActivity());
        editText.setPadding(16, 16, 16, 16);
        if (phoneNumber_tv.getText() != null) {
            editText.setText(phoneNumber_tv.getText());
        }

        builder.setTitle("Set Phone")
                .setView(editText)
                .setPositiveButton("Save", (a, b) -> {
                    CurrentUser.updatePhone(editText.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                                    phoneNumber_tv.setText(CurrentUser.getCurrentUser().getPhoneNumber());
                                } else {
                                    Toast.makeText(getActivity(), "failed :" + task.getResult(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }).setNeutralButton("Cancel", null).create().show();
    }
}

