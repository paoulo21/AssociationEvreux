package com.example.assoevreux.Association;

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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.assoevreux.Categorie.CategorieActivity;
import com.example.assoevreux.Donnee.AssosAdapter;
import com.example.assoevreux.Donnee.AssosApplication;
import com.example.assoevreux.databinding.ActivityAssociationBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        setMail();
        setAppel();
        String actionText = association.getAction().replace("•", "\n•");
        ui.action.setText(actionText);
        ui.territoire.setText(association.getTerritoireIntervention());
        ui.publicCible.setText(association.getPublicCible());
        ImageButton back = ui.backButton;
        back.setOnClickListener(btn -> finish());
        setCategories();
        addWebSite();
    }

    public void setAppel(){
        SpannableString spannableString = new SpannableString(association.getTelephone());

        // Expression régulière pour détecter les numéros de téléphone
        Pattern phonePattern = Pattern.compile("(\\d{2} \\d{2} \\d{2} \\d{2} \\d{2})");
        Matcher phoneMatcher = phonePattern.matcher(association.getTelephone());

        while (phoneMatcher.find()) {
            final String phoneNumber = phoneMatcher.group().replace(" ", ""); // Supprimer les espaces
            int startIndex = phoneMatcher.start();
            int endIndex = phoneMatcher.end();

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);
                }
            };
            spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        ui.appelText.setText(spannableString); // Remplacez ui.telephoneText par le TextView approprié
        ui.appelText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setMail() {
        // Adresses e-mail
        SpannableString spannableString = new SpannableString(association.getEmail());
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}");
        Matcher emailMatcher = emailPattern.matcher(association.getEmail());
        while (emailMatcher.find()) {
            final String email = emailMatcher.group();
            int startIndex = emailMatcher.start();
            int endIndex = emailMatcher.end();
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                    startActivity(intent);
                }
            };
            spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        ui.mailText.setText(spannableString);
        ui.mailText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void addWebSite(){
        String text = association.getSiteWeb();

        // Expression régulière qui détècte les liens
        Pattern urlPattern = Pattern.compile("((https?|ftp|file)://|www\\.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher matcher = urlPattern.matcher(text);

        SpannableString spannableString = new SpannableString(text);

        while (matcher.find()) {
            final String url = matcher.group();
            int startIndex = matcher.start();
            int endIndex = matcher.end();
            //Ajoute le lien vers le site au texte correspondant
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            };
            spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        ui.siteWeb.setText(spannableString);
        ui.siteWeb.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setCategories() {
        String categoriesAffichees = String.join("", association.getCategorie());
        SpannableString categorieText = new SpannableString(categoriesAffichees);
        Context context = this;
        int startIndex = 0;
        for (String categorie : association.getCategorie()) {
            categorie = categorie.trim();
            String finalCategorie = categorie;
            //Ajoute le lien vers chaque catégorie au texte de celle ci
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent intent = new Intent(context, CategorieActivity.class);
                    intent.putExtra("categorie", finalCategorie);
                    startActivity(intent);
                }
            };
            int endIndex = startIndex + categorie.length()+1;
            categorieText.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            startIndex = endIndex;
        }
        ui.categorie.setText(categorieText);
        ui.categorie.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onItemClick(int position) {

    }
}