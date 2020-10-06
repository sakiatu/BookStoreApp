package com.beecoder.bookstore;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.beecoder.bookstore.Authentication.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            showSplashScreen();
            openAuthActivity();
            finish();
        }
    }

    private void openAuthActivity() {

        startActivity(new Intent(this, AuthActivity.class));
    }

    private void showSplashScreen() {
       startActivity(new Intent(this,SplashScreen.class));
    }
}
