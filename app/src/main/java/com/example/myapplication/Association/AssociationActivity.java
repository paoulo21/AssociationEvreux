package com.example.myapplication.Association;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Categorie.CategorieActivity;
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
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        AssosApplication application = (AssosApplication) getApplication();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        association = application.getAssociationList().get(position);
        ui.nom.setText(association.getNom());
        ui.president.setText(association.getPresident());
        ui.adresse.setText(association.getAdresse());
        ui.description.setText(association.getDescription());
        Glide.with(this).load(association.getImageURL()).into(ui.image);
        ui.appelText.setText(association.getTelephone());
        ui.mailText.setText(association.getEmail());
        ImageView appel = ui.appelIcon;
        appel.setOnClickListener(btn -> {
            Intent toAppel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + association.getTelephone()));
            startActivity(toAppel);
        });
        ImageView mail = ui.mailIcon;
        mail.setOnClickListener(btn -> {
            Intent toMail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + association.getEmail()));
            startActivity(toMail);
        });
        ImageButton back = ui.backButton;
        back.setOnClickListener(btn -> finish());
        setCategories();
        ui.action.setText(association.getAction());
        ui.territoire.setText(association.getTerritoireIntervention());
        ui.publicCible.setText(association.getPublicCible());
    }

    private void setCategories() {
        String categoriesAffichees = String.join(" \n •", association.getCategorie());
        SpannableString categorieText = new SpannableString(categoriesAffichees);
        Context context = this;
        int startIndex = 0;
        for (String categorie : association.getCategorie()) {
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // Action pour la catégorie
                    Intent intent = new Intent(context, CategorieActivity.class);
                    intent.putExtra("categorie", categorie);
                    startActivity(intent);
                }
            };
            int endIndex = startIndex + categorie.length();
            categorieText.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            startIndex = endIndex + 2; // +2 pour la virgule et l'espace
        }
        ui.categorie.setText(categorieText);
        ui.categorie.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onItemClick(int position) {

    }
}