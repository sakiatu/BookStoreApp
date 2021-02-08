package com.beecoder.bookstore;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CurrentUser {
    private static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

    public static Task<Void> updatePhotoUrl(String photoUrl) {
        currentUser.setPhotoUrl(photoUrl);
        return userDoc.set(currentUser);
    }
}
