package com.beecoder.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.beecoder.bookstore.Authentication.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            openAuthActivity();
            finish();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tv_title_toolbar);
        title.setText("BookStore");
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(this::onMenuItemSelected);
    }

    private boolean onMenuItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_sell:
                openSellingActivity();
                break;
        }
        return true;
    }

    private void openSellingActivity() {

    }

    private void openAuthActivity() {
        startActivity(new Intent(this, AuthActivity.class));
    }
}
