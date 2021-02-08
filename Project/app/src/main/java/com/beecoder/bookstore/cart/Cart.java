package com.beecoder.bookstore.cart;

public class Cart {
private String bookId;
private String userId;

    public Cart() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Cart(String bookId, String userId) {
        this.bookId = bookId;
        this.userId = userId;
    }
}
