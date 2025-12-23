package com.example.assoevreux.Template;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.assoevreux.Annonce.AnnonceActivity;
import com.example.assoevreux.Annonce.PostAnnonceActivity;
import com.example.assoevreux.Application.AssosApplication;
import com.example.assoevreux.Categorie.CategorieActivity;
import com.example.assoevreux.Connexion.LoginActivity;
import com.example.assoevreux.Donnee.AssosActivity;
import com.example.assoevreux.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected ActionBarDrawerToggle toggle;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DrawerLayout drawer;
    NavigationView nav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId()); // Chaque activité enfant fournira son propre layout

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Ferme le menu quand on revient sur l'activité
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        // Met à jour le menu en fonction de l'état de connexion de l'utilisateur
        if (currentUser == null) {
            nav.getMenu().findItem(R.id.nav_connexion).setVisible(true);
            nav.getMenu().findItem(R.id.nav_post_annonce).setVisible(false);
            nav.getMenu().findItem(R.id.deconnexion).setVisible(false);
        } else {
            nav.getMenu().findItem(R.id.nav_connexion).setVisible(false);
            nav.getMenu().findItem(R.id.nav_post_annonce).setVisible(true);
            nav.getMenu().findItem(R.id.deconnexion).setVisible(true);
            if (nav.getHeaderCount() == 0) {
                View headerView = nav.inflateHeaderView(R.layout.nav_header);
                TextView textViewNomAssociation = headerView.findViewById(R.id.textView);
                textViewNomAssociation.setText(AssosApplication.getAssociationUtilisateur());
                ImageView imageView = headerView.findViewById(R.id.imageView);
                Glide.with(this).load(AssosApplication.getAssociationUtilisateur()).into(imageView);
            }
        }
    }


    // Méthode abstraite que chaque activité enfant devra implémenter pour fournir son layout
    protected abstract int getLayoutResourceId();

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Gestion d'un clic sur un élément du menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, AssosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_cate) {
            Intent intent = new Intent(this, CategorieActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_annonce) {
            Intent intent = new Intent(this, AnnonceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_connexion) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_post_annonce) {
            Intent intent = new Intent(this, PostAnnonceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.deconnexion) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(getIntent());
        }
        return true;
    }
    //Met en place le menu à partir des infos fournises
    public void setMenu(DrawerLayout drawerLayout, NavigationView navView){
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        navView.setNavigationItemSelectedListener(this);

        drawer = drawerLayout;
        nav = navView;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            navView.getMenu().findItem(R.id.nav_connexion).setVisible(true);
            navView.getMenu().findItem(R.id.nav_post_annonce).setVisible(false);
            navView.getMenu().findItem(R.id.deconnexion).setVisible(false);
        } else {
            navView.getMenu().findItem(R.id.nav_connexion).setVisible(false);
            navView.getMenu().findItem(R.id.nav_post_annonce).setVisible(true);
            navView.getMenu().findItem(R.id.deconnexion).setVisible(true);
            if (nav.getHeaderCount() == 0) {
                View headerView = nav.inflateHeaderView(R.layout.nav_header);
                TextView textViewNomAssociation = headerView.findViewById(R.id.textView);
                textViewNomAssociation.setText(AssosApplication.getAssociationUtilisateur());
                ImageView imageView = headerView.findViewById(R.id.imageView);
                Glide.with(this).load(AssosApplication.getAssociationUtilisateur()).into(imageView);
            }
        }
    }
}