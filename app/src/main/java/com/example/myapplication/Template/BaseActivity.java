package com.example.myapplication.Template;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.Categorie.CategorieActivity;
import com.example.myapplication.Donnee.MainActivity;
import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId()); // Chaque activité enfant fournira son propre layout

        drawerLayout = findViewById(R.id.drawerLayout); // Assurez-vous que l'ID est correct dans vos layouts
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close); // Utilisez les chaînes appropriées
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
    }

    // Méthode abstraite que chaque activité enfant devra implémenter pour fournir son layout
    protected abstract int getLayoutResourceId();

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Implémentez la méthode onNavigationItemSelected ici ou dans les activités enfants
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        drawerLayout.closeDrawers();
        if (id == R.id.nav_home) {
            drawerLayout.closeDrawers();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // Ajouter ce drapeau
            startActivity(intent);
        } else if (id == R.id.nav_cate) {
            drawerLayout.closeDrawers();
            Intent intent = new Intent(this, CategorieActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        drawerLayout.closeDrawers();
        return true;
    }
}