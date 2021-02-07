package com.beecoder.bookstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beecoder.bookstore.Authentication.AuthActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {
    TextView username;
    ImageButton editUsername;
    ImageView addPhone;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    DocumentReference reference=firestore.collection("Username").document();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.profile_layout, container, false);
       // Button signOut = layout.findViewById(R.id.signOut);
        TextView email = layout.findViewById(R.id.email);
        username = layout.findViewById(R.id.username);
        editUsername = layout.findViewById(R.id.edit_username);
        addPhone=layout.findViewById(R.id.edit_phone_number);

        editUsername.setOnClickListener(view -> editUser());
        addPhone.setOnClickListener(view -> AddPhone());
        setValue();

        email.setText(user.getEmail());

        //signOut.setOnClickListener(v -> logout());
        return layout;
    }
    private void AddPhone() {
        userInfo("Phone");
    }

    private void editUser() {
        userInfo("Username");
    }

    private void setValue() {
        reference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            String naam = documentSnapshot.getString("name");
                            username.setText(naam);
                            //Log.d(TAG, "onSuccess: ");

                        }else
                        {
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }

        });
}


    private void userInfo(String type)
    {
        AlertDialog.Builder setUsername = new AlertDialog.Builder(getActivity());
        setUsername.setTitle(type);
        Users users=new Users();
        final EditText value= new EditText(getActivity());
        setUsername.setView(value);
        setUsername.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 if(type.equals("Username"))
                 {
                     users.setName(value.getText().toString());
                     users.setUserid(user.getUid());
                 }
                 else
                 {
                     users.setPhoneNumber(value.getText().toString());
                 }
                reference.set(users);
            }
        });

        setUsername.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        setUsername.create().show();
    }

    }

