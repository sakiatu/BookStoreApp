package com.beecoder.bookstore.database;

import com.beecoder.bookstore.cart.Cart;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CartDatabase {
    private CollectionReference cartRef = FirebaseFirestore.getInstance().collection("Cart");
    public Task<DocumentReference> addToCart(Cart cart)
    {
        return cartRef.add(cart);
    }
    
}
