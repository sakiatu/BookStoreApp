package com.beecoder.bookstore.database;

import com.beecoder.bookstore.cart.Cart;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class CartDatabase {
    public Task<Void> addToCart(Cart cart) {
        return FirebaseFirestore.getInstance().collection("Books").document(cart.getBookId())
                .update("cart", FieldValue.arrayUnion(cart.getUserId()));
    }

}
