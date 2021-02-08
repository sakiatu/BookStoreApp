package com.beecoder.bookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class BookAdapter extends FirestoreRecyclerAdapter<Book, BookAdapter.bookHolder> {

    private Context context;
    public OnAddToCartClickListener onAddToCartClickListener;

    public interface OnAddToCartClickListener {
        void onClick(DocumentSnapshot snapshot, Button button);
    }

    public void setOnAddToCartClickListener(OnAddToCartClickListener onAddToCartClickListener) {
        this.onAddToCartClickListener = onAddToCartClickListener;
    }

    public BookAdapter(@NonNull FirestoreRecyclerOptions<Book> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull bookHolder holder, int position, @NonNull Book book) {
        holder.bookName.setText(book.getTitle());
        holder.authorName.setText(book.getAuthorName());
        holder.edition.setText(book.getEdition());
        holder.price.setText(book.getPrice());
        holder.category.setText(book.getCategory());
        holder.btn_addCart.setOnClickListener(v -> onAddToCartClickListener
                .onClick(
                        getSnapshots().getSnapshot(position),
                        holder.btn_addCart));


        Glide.with(context)
                .load(book.getImageUrl())
                .centerCrop()
                .into(holder.bookCover);

    }

    @NonNull
    @Override
    public bookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new bookHolder(view);
    }

    class bookHolder extends RecyclerView.ViewHolder {

        TextView bookName, authorName, edition, price, category;
        ImageView bookCover;
        Button btn_addCart;

        public bookHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.txt_bookName);
            authorName = itemView.findViewById(R.id.txt_authorName);
            edition = itemView.findViewById(R.id.txt_eiditipn);
            price = itemView.findViewById(R.id.txt_price);
            category = itemView.findViewById(R.id.txt_category);
            bookCover = itemView.findViewById(R.id.bookCover);
            btn_addCart = itemView.findViewById(R.id.btn_addCart);
        }
    }
}
