package com.example.assoevreux.Donnee;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assoevreux.Association.Association;
import com.example.assoevreux.R;
import com.example.assoevreux.Template.MenuActivity;
import com.example.assoevreux.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends MenuActivity implements AssosApplication.OnAssociationsLoadedListener, NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding ui;
    private List<Association> assoList;
    AssosApplication application;
    ProgressBar progressBar;
    private static boolean loaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(ui.getRoot());
        progressBar = ui.progressBar;
        if (!loaded) { // Première création
            progressBar.setVisibility(View.VISIBLE);
        }
        application = (AssosApplication) getApplication();
        application.setOnAssociationsLoadedListener(this);

        toggle = new ActionBarDrawerToggle(this, ui.drawerLayout, R.string.open, R.string.close);
        ui.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ui.navView.setNavigationItemSelectedListener(this);
        onAssociationsLoaded(application.getAssociationList());

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (super.onOptionsItemSelected(item)) { // Appeler la méthode de BaseActivity
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
            progressBar.setVisibility(View.GONE);
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
        loaded = true;
    }

}