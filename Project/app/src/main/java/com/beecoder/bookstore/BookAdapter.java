package com.beecoder.bookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class BookAdapter extends FirestoreRecyclerAdapter <Book, BookAdapter.bookHolder> {

    Context context;


    public BookAdapter(@NonNull FirestoreRecyclerOptions<Book> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull bookHolder holder, int position, @NonNull Book book) {
        holder.bookName.setText(book.getTitle());
        holder.authorName.setText(book.getAuthorName());
        holder.edition.setText(book.getEdition());
        holder.price.setText(book.getPrice());
        holder.category.setText(book.getCategory());
        Glide.with(context).load(book.getImageUrl()).into(holder.bookCover);


    }

    @NonNull
    @Override
    public bookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.book_item,parent,false);
        return new bookHolder(view);
    }

    class bookHolder extends RecyclerView.ViewHolder{

        TextView bookName, authorName,edition,price,category;
        ImageView bookCover;

        public bookHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.txt_bookName);
            authorName=itemView.findViewById(R.id.txt_authorName);
            edition=itemView.findViewById(R.id.txt_eiditipn);
            price=itemView.findViewById(R.id.txt_price);
            category=itemView.findViewById(R.id.txt_category);
            bookCover=itemView.findViewById(R.id.bookCover);
        }
    }
}
