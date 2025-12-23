package com.example.assoevreux.Annonce;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assoevreux.Application.AssosApplication;
import com.example.assoevreux.R;
import com.example.assoevreux.Template.MenuActivity;
import com.example.assoevreux.databinding.ActivityAnnonceBinding;

import java.util.List;

public class AnnonceActivity extends MenuActivity {
    AssosApplication application;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        com.example.assoevreux.databinding.ActivityAnnonceBinding ui = ActivityAnnonceBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(ui.getRoot());
        setMenu(ui.drawerLayout, ui.navView);
        application = (AssosApplication) getApplication();
        List<Annonce> annonceList = application.getAnnonceList();
        AnnonceAdapter adapter = new AnnonceAdapter(annonceList);
        ui.recycler.setAdapter(adapter);
        ui.recycler.setHasFixedSize(true);
        ui.recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_annonce;
    }

}
