package com.example.assoevreux.Categorie;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;

import com.example.assoevreux.Association.Association;
import com.example.assoevreux.Donnee.AssosApplication;
import com.example.assoevreux.R;
import com.example.assoevreux.Template.MenuActivity;
import com.example.assoevreux.databinding.ActivityCategorieBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CategorieActivity extends MenuActivity {

    private ExpandableListView expandableListView;
    private Map<String, List<Association>> associationsByCategory;
    private ActivityCategorieBinding ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityCategorieBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        expandableListView = ui.expandableListView;

        // Récupérer les associations et les organiser par catégorie
        associationsByCategory = new TreeMap<>();
        AssosApplication application = (AssosApplication) getApplication();
        List<Association> allAssociations = application.getAssociationList();

        for (Association association : allAssociations) {
            for (String categories : association.getCategorie()) {
                for (String category : categories.split("/")) {
                    category = category.trim();
                    if (!associationsByCategory.containsKey(category)) {
                        associationsByCategory.put(category, new ArrayList<>());
                    }
                    associationsByCategory.get(category).add(association);
                }
            }
        }
        // Créer un adaptateur pour la map de catégories
        CategorieAdapter adapter = new CategorieAdapter(this,associationsByCategory);
        expandableListView.setAdapter(adapter);

        setMenu(ui.drawerLayout,ui.navView);

        SearchView searchView = ui.searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Filtrer les résultats lorsque l'utilisateur appuie sur Entrée
                adapter.filter(query);
                expandableListView.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrer les résultats en temps réel lorsque l'utilisateur tape
                adapter.filter(newText);
                expandableListView.setAdapter(adapter);
                return true;
            }
        });
        //récupérer la catégorie cliqué dans association et l'ouvrir
        Intent intent = getIntent();
        if (intent.hasExtra("categorie")){
            searchView.setQuery(intent.getStringExtra("categorie"),true);
            searchView.clearFocus();
            expandableListView.expandGroup(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ui.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            ui.drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (super.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_categorie;
    }

}