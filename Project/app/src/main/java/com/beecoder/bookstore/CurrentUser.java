package com.beecoder.bookstore;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CurrentUser {
    private static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static StorageReference photoRef = FirebaseStorage.getInstance().getReference().child("images").child(user.getUid());
    private static DocumentReference userDoc = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());

    private static User currentUser = new User();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentUser.currentUser = currentUser;
    }

    public static DocumentReference getUserDoc() {
        return userDoc;
    }

    public static Task<Void> updatePhone(String phoneNumber) {
        currentUser.setPhoneNumber(phoneNumber);
        return userDoc.set(currentUser);
    }

    public static UploadTask uploadPhoto(Uri uri) {
        photoRef = photoRef.child(uri.hashCode() + "");
        return photoRef.putFile(uri);
    }

    public static Task<Void> updatePhotoUrl(String url) {
        currentUser.setPhotoUrl(url);
        return userDoc.set(currentUser);
    }

    public static StorageReference getPhotoRef() {
        return photoRef;
    }
}
