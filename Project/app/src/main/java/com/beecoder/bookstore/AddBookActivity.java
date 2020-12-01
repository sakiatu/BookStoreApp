package com.beecoder.bookstore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "AddBooks";
    private EditText text1, text2, text3, text4;
    private Button btn;
    private Spinner spinner;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private String[] category = {"Math", "EEE", "Physics", "Data Structure", "Algorithm", "Story Books"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);
        text1 = findViewById(R.id.edit_txt_title);
        text2 = findViewById(R.id.edit_txt_author);
        text3 = findViewById(R.id.edit_txt_edition);
        text4 = findViewById(R.id.edit_txt_price);

        btn = findViewById(R.id.btn_add);

        spinner = findViewById(R.id.spin_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tv_title_toolbar);
        title.setText("Add Book");

    }

    public void addBook(View view) {
        String title = text1.getText().toString();
        String authorName = text2.getText().toString();
        String edition = text3.getText().toString();
        String price = text4.getText().toString();
        String categoryName = spinner.getSelectedItem().toString();

        /*Map<String, String> map = new HashMap<>();
        map.put("Title", bookName);
        map.put("Author", authorName);
        map.put("Edition", edition);
        map.put("Price", price);*/

        firestore.collection("Books").add(new Book(title, authorName, edition, price, categoryName))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "request sent to admin...", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Log.d(TAG, "Failed"));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}