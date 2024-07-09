package com.example.myapplication.Donnee;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Association.Association;
import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DonneeApplication.OnAssociationsLoadedListener {
    private ActivityMainBinding ui;
    private List<Association> assoList;
    DonneeApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(ui.getRoot());
        application = (DonneeApplication) getApplication();
        application.setOnAssociationsLoadedListener(this);
    }

    private void onItemClick(int position) {
        Association asso = assoList.get(position);

    }

    @Override
    public void onAssociationsLoaded(List<Association> associations) {
        application = (DonneeApplication) getApplication();
        assoList = application.getAssociationList();
        AssosAdapter adapter = new AssosAdapter(assoList);
        ui.recycler.setAdapter(adapter);
        ui.recycler.setHasFixedSize(true);
        ui.recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssosAdapter(assoList);
        adapter.setOnItemClickListener(this::onItemClick);
    }
}