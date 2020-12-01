package com.beecoder.bookstore;

public class Book {

    String title;
    String authorName;
    String Edition;
    String Price;
    String Category;

    public Book(String title,String authorName,String Edition,String Price,String Category) {
        this.title = title;
        this.authorName=authorName;
        this.Edition= Edition;
        this.Price=Price;
        this.Category=Category;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public String getEdition() {
        return Edition;
    }

    public void setEdition(String edition) {
        Edition = edition;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", Edition='" + Edition + '\'' +
                ", Price='" + Price + '\'' +
                ", Category='" + Category + '\'' +
                '}';
    }
}
