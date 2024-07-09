package fr.iutlille.xmen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;
import java.util.zip.Inflater;

import fr.iutlille.xmen.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        // obtenir la liste
        XMenApplication application = (XMenApplication) getApplication();
        List<XMen> liste = application.getListe();

        // créer l'adaptateur
        XMenAdapter adapter = new XMenAdapter(liste);

        // fournir l'adaptateur au recycler
        ui.recycler.setAdapter(adapter);

        ui.recycler.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        ui.recycler.setLayoutManager(lm);
    }
}