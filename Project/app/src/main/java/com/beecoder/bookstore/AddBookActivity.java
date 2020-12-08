package com.beecoder.bookstore;

import android.content.Intent;
import android.net.Uri;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final int IMG_REQUEST_ID=10;
    private static final String TAG = "AddBooks";
    private EditText text1, text2, text3, text4;
    private Button btn;
    private Spinner spinner;
    private Button btnUpload;

    private Uri uri;

    String filePath;

    private UploadTask uploadTask;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private FirebaseStorage storage;
    private StorageReference reference;

    private String[] category = {"Math", "Data Structure", "Algorithm", "Story Books"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);
        text1 = findViewById(R.id.edit_txt_title);
        text2 = findViewById(R.id.edit_txt_author);
        text3 = findViewById(R.id.edit_txt_edition);
        text4 = findViewById(R.id.edit_txt_price);

        btn = findViewById(R.id.btn_add);
        btnUpload=findViewById(R.id.btn_upload_img);

        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();

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


        firestore.collection("Books").add(new Book(title, authorName, edition, price, categoryName,filePath))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "request sent to admin...", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Log.d(TAG, "Failed"));

        saveImage();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Selected Image"),IMG_REQUEST_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMG_REQUEST_ID && resultCode==RESULT_OK && data != null&& data.getData()!=null)
        {
            uri=data.getData();
        }
    }


    public void saveImage()
    {
        /*ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();*/

        if(uri!= null)
        {
            StorageReference storageReference = reference.child("image/" + UUID.randomUUID().toString());

            uploadTask = storageReference.putFile(uri);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                filePath= task.getResult().toString();
                                System.out.println("Successful"+ filePath);
                            } else {

                            }
                        }
                    });
        }
    }
}