package com.example.myapplication.Association;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Donnee.AssosAdapter;
import com.example.myapplication.Donnee.AssosApplication;
import com.example.myapplication.databinding.ActivityAssociationBinding;

public class AssociationActivity extends AppCompatActivity implements
        AssosAdapter.OnItemClickListener{
    private ActivityAssociationBinding ui;
    private Association association;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityAssociationBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(ui.getRoot());
        AssosApplication application = (AssosApplication) getApplication();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        association = application.getAssociationList().get(position);
        ui.nom.setText(association.getNom());
        ui.president.setText(association.getPresident());
        ui.adresse.setText(association.getAdresse());
        ui.description.setText(association.getDescription());
        Glide.with(this).load(association.getImageURL()).into(ui.image);
        ImageView appel = ui.appel;
        appel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View btn) {
                Intent toAppel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + association.getTelephone()));
                startActivity(toAppel);
            }
        });
        ImageView mail = ui.mail;
        mail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View btn) {
                Intent toMail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + association.getEmail()));
                startActivity(toMail);
            }
        });
        ImageButton back = ui.backButton;
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View btn) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }
}