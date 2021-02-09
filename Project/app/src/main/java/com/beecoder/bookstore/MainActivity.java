package com.beecoder.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.beecoder.bookstore.Authentication.AuthActivity;
import com.beecoder.bookstore.cart.CartActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseUser user;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            openAuthActivity();
            finish();
        } else {
            setUserValues();
            setupToolbar();
            setUpNavigationDrawer(savedInstanceState);
        }
    }

    private void setUserValues() {
        CurrentUser.getCurrentUser().setName(user.getDisplayName());
        CurrentUser.getCurrentUser().setEmail(user.getEmail());
        CurrentUser.getCurrentUser().setId(user.getUid());
        CurrentUser.getUserDoc()
                .get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists() && CurrentUser.getCurrentUser().getId().equals(user.getUid())) {
                User currentUser = snapshot.toObject(User.class);
                CurrentUser.getCurrentUser().setPhoneNumber(currentUser.getPhoneNumber());
                CurrentUser.getCurrentUser().setPhotoUrl(currentUser.getPhotoUrl());

                //Toast.makeText(this, "Asi mamah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openAuthActivity() {
        startActivity(new Intent(this, AuthActivity.class));
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpNavigationDrawer(Bundle saveInstanceState) {
        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawerContent, R.string.closeDrawerContent);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();

        if (saveInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home_item);
        }

        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.username);
        TextView email = headerView.findViewById(R.id.email);
        username.setText(user.getDisplayName());
        email.setText(user.getEmail());
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!(fragment instanceof HomeFragment))
            setFragment(new HomeFragment());
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.setCheckedItem(item.getItemId());
        switch (item.getItemId()) {
            case R.id.home_item:
                setFragment(new HomeFragment());
                break;
            case R.id.catalogue_item:
                setFragment(new CatalogueFragment());
                break;
            case R.id.profile_item:
                setFragment(new ProfileFragment(this));
                break;
            case R.id.cart_item:
                startActivity(new Intent(this,CartActivity.class));
                break;
            case R.id.contact_item:
                setFragment(new ContactFragment());
                break;
            case R.id.logOut_item:
                AuthUI.getInstance().signOut(MainActivity.this);
                startActivity(new Intent(MainActivity.this, AuthActivity.class));
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
