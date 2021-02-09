package com.beecoder.bookstore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beecoder.bookstore.cart.CartActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    private final int IMG_REQUEST_ID=10;

    private TextView username, phoneNumber_tv,email;
    private ImageView editPhone, imgBtn_photoEdit,img_userProfile,imgBtn_cart;
    private Button btn_uploadProfileImg;
    private Uri uri;
    private Context context;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.profile_layout, container, false);
        user=FirebaseAuth.getInstance().getCurrentUser();
        setViews(layout);
        return layout;
    }

    ProfileFragment(Context context)
    {
        this.context=context;
    }

    private void setViews(View layout) {
        email = layout.findViewById(R.id.email);
        phoneNumber_tv = layout.findViewById(R.id.phone_number);
        username = layout.findViewById(R.id.username);
        editPhone = layout.findViewById(R.id.edit_phone_number);
        img_userProfile=layout.findViewById(R.id.img_userProfile);
        imgBtn_photoEdit = layout.findViewById(R.id.imgBtn_photoEdit);
        btn_uploadProfileImg=layout.findViewById(R.id.btn_uploadProfileImg);
        imgBtn_cart=layout.findViewById(R.id.imgBtn_cart);
        editPhone.setOnClickListener(view -> showEditPhoneDialogue());
        imgBtn_cart.setOnClickListener(view -> openCart());
        if (CurrentUser.getCurrentUser() != null && CurrentUser.getCurrentUser().getId().equals(user.getUid())) {
            email.setText(CurrentUser.getCurrentUser().getEmail());
            username.setText(CurrentUser.getCurrentUser().getName());
            phoneNumber_tv.setText(CurrentUser.getCurrentUser().getPhoneNumber());
            img_userProfile.setImageURI(uri);
        }
        imgBtn_photoEdit.setOnClickListener(v -> openGallery());
        btn_uploadProfileImg.setOnClickListener(view -> uploadImage());
        Glide.with(context)
                .load(CurrentUser.getCurrentUser().getPhotoUrl())
                .centerCrop()
                .into(img_userProfile);

    }

    private void openCart() {
        startActivity(new Intent(getActivity(),CartActivity.class));
    }

    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Selected Image"), IMG_REQUEST_ID);
    } @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST_ID && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            img_userProfile.setImageURI(uri);
        }
    }
    private void uploadImage()
    {
        CurrentUser.updatePhotoUrl(uri.toString())
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                        Glide.with(context)
                                .load(CurrentUser.getCurrentUser().getPhotoUrl())
                                .centerCrop()
                                .into(img_userProfile);
                    }else
                    {
                        Toast.makeText(getActivity(), "failed :" + task.getResult(), Toast.LENGTH_SHORT).show();
                    }
                });
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

