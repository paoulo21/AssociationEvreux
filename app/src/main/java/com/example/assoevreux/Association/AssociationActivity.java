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
import android.widget.ImageView;

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
        String actionText = association.getAction().replace("•", "\n•");
        ui.action.setText(actionText);
        ui.territoire.setText(association.getTerritoireIntervention());
        ui.publicCible.setText(association.getPublicCible());
        addWebSite();
    }

    public void addWebSite(){
        String text = association.getSiteWeb();

        // Use a regular expression to find all URLs in the text
        Pattern urlPattern = Pattern.compile("((https?|ftp|file)://|www\\.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher matcher = urlPattern.matcher(text);

        SpannableString spannableString = new SpannableString(text);

        while (matcher.find()) {
            final String url = matcher.group();
            int startIndex = matcher.start();
            int endIndex = matcher.end();

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // Open the URL in a browser
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            };
            spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // Set the SpannableString to the TextView and enable clickable links
        ui.siteWeb.setText(spannableString);
        ui.siteWeb.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setCategories() {
        String categoriesAffichees = String.join(" \n•", association.getCategorie());
        SpannableString categorieText = new SpannableString(categoriesAffichees);
        Context context = this;
        int startIndex = 0;
        for (String categorie : association.getCategorie()) {
            categorie = categorie.trim();
            String finalCategorie = categorie;
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent intent = new Intent(context, CategorieActivity.class);
                    intent.putExtra("categorie", finalCategorie);
                    startActivity(intent);
                }
            };
            int endIndex = startIndex + categorie.length();
            categorieText.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            startIndex = endIndex + 3;
        }
        ui.categorie.setText(categorieText);
        ui.categorie.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onItemClick(int position) {

    }
}