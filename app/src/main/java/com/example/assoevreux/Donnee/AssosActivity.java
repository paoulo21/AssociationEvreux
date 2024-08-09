package com.example.assoevreux.Donnee;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assoevreux.Association.Association;
import com.example.assoevreux.R;
import com.example.assoevreux.Template.MenuActivity;
import com.example.assoevreux.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class AssosActivity extends MenuActivity implements AssosApplication.OnAssociationsLoadedListener, NavigationView.OnNavigationItemSelectedListener {
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
        if (!loaded) { // Affiche la barre de progression uniquement pendant la première création
            progressBar.setVisibility(View.VISIBLE);
        }
        application = (AssosApplication) getApplication();
        application.setOnAssociationsLoadedListener(this);
        setMenu(ui.drawerLayout,ui.navView);

        onAssociationsLoaded(application.getAssociationList());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Ferme le menu quand on revient sur l'activité
        if (ui.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            ui.drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (super.onOptionsItemSelected(item)) { // Appeler la méthode de MenuActivity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAssociationsLoaded(List<Association> associations) { // Quand les associations on fini d'être récuperé
        application = (AssosApplication) getApplication();
        assoList = application.getAssociationList();
        AssosAdapter adapter = new AssosAdapter(assoList);
        ui.recycler.setAdapter(adapter);
        ui.recycler.setHasFixedSize(true);
        ui.recycler.setLayoutManager(new LinearLayoutManager(this));
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