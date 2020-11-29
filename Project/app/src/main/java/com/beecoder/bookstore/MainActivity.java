package com.beecoder.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.beecoder.bookstore.Authentication.AuthActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser user;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = FirebaseAuth.getInstance().getCurrentUser();
        fab=findViewById(R.id.fab_edit);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddBooks.class);
                startActivity(intent);
            }
        });

        if (user == null) {
            openAuthActivity();
            finish();
        }
    }

    private void openAuthActivity() {
        startActivity(new Intent(this, AuthActivity.class));
    }
}
