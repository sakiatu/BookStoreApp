package com.beecoder.bookstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class BookRecylcerViewAdapter extends FirestoreRecyclerAdapter <Book, BookRecylcerViewAdapter.bookHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BookRecylcerViewAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull bookHolder holder, int position, @NonNull Book book) {
        holder.bookName.setText(book.getTitle());
        holder.authorName.setText(book.getAuthorName());
        holder.edition.setText(book.getEdition());
        holder.price.setText(book.getPrice());
        holder.category.setText(book.getCategory());
    }

    @NonNull
    @Override
    public bookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_list,parent,false);
        return new bookHolder(view);
    }

    class bookHolder extends RecyclerView.ViewHolder{

        TextView bookName, authorName,edition,price,category;

        public bookHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.txt_bookname);
            authorName=itemView.findViewById(R.id.txt_authorName);
            edition=itemView.findViewById(R.id.txt_eiditipn);
            price=itemView.findViewById(R.id.txt_price);
            category=itemView.findViewById(R.id.txt_category);
        }
    }
}
