package com.example.myapplication.Donnee;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Association.Association;
import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AssosApplication.OnAssociationsLoadedListener {
    private ActivityMainBinding ui;
    private List<Association> assoList;
    AssosApplication application;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(ui.getRoot());
        application = (AssosApplication) getApplication();
        application.setOnAssociationsLoadedListener(this);
        progressBar = ui.progressBar;
        progressBar.setVisibility(View.VISIBLE);
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
        SearchView searchView = ui.searchView;
        AssosAdapter finalAdapter = adapter;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Filtrer les résultats lorsque l'utilisateur appuie sur Entrée
                finalAdapter.filter(query);
                ui.recycler.setAdapter(finalAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtrer les résultats en temps réel lorsque l'utilisateur tape
                finalAdapter.filter(newText);
                ui.recycler.setAdapter(finalAdapter);
                return true;
            }
        });
        progressBar.setVisibility(View.GONE);

    }
}