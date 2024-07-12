package com.example.myapplication.Donnee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Association.Association;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AssosApplication.OnAssociationsLoadedListener, NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding ui;
    private List<Association> assoList;
    AssosApplication application;
    ProgressBar progressBar;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(ui.getRoot());
        progressBar = ui.progressBar;
        if (savedInstanceState == null) { // Première création
            progressBar.setVisibility(View.VISIBLE);
        }
        application = (AssosApplication) getApplication();
        application.setOnAssociationsLoadedListener(this);
        toggle = new ActionBarDrawerToggle(this, ui.drawerLayout, R.string.open, R.string.close);
        ui.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ui.navView.setNavigationItemSelectedListener(this);
        onAssociationsLoaded(application.getAssociationList());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onItemClick(int position) {
        Association asso = assoList.get(position);

    }

    @Override
    public void onAssociationsLoaded(List<Association> associations) {
        application = (AssosApplication) getApplication();
        assoList = application.getAssociationList();
        AssosAdapter adapter = new AssosAdapter(assoList);
        ui.recycler.setAdapter(adapter);
        ui.recycler.setHasFixedSize(true);
        ui.recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(this::onItemClick);
        application.setOnAssociationsLoadedListener(association -> {
            progressBar.setVisibility(View.GONE); // Masquer la barre de progression ICI
        });
        SearchView searchView = ui.searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Filtrer les résultats lorsque l'utilisateur appuie sur Entrée
                adapter.filter(query);
                ui.recycler.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrer les résultats en temps réel lorsque l'utilisateur tape
                adapter.filter(newText);
                ui.recycler.setAdapter(adapter);
                return true;
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        int id = item.getItemId();
        if (id == R.id.nav_home && !(this instanceof MainActivity)) {
            Log.i("test", "test");
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("position",0);
            this.startActivity(intent);
        }
        //ui.drawerLayout.closeDrawers();
        return true;
    }
}