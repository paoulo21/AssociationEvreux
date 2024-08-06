package com.example.assoevreux.Template;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.assoevreux.Categorie.CategorieActivity;
import com.example.assoevreux.Donnee.MainActivity;
import com.example.assoevreux.R;
import com.google.android.material.navigation.NavigationView;

public abstract class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId()); // Chaque activité enfant fournira son propre layout
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

    //Gestion d'un clic sur un élément du menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_cate) {
            Intent intent = new Intent(this, CategorieActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        return true;
    }

    public void setMenu(DrawerLayout drawerLayout, NavigationView navView){
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        navView.setNavigationItemSelectedListener(this);
    }
}