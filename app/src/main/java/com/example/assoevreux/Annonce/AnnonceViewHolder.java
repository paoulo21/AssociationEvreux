package com.example.assoevreux.Annonce;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assoevreux.databinding.AnnonceCardBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AnnonceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final AnnonceCardBinding ui;
    private AnnonceAdapter.OnItemClickListener listener;
    // AssosApplication application; // Vous n'aurez peut-être pas besoin de cela pour les annonces
    public int itemPos;

    public AnnonceViewHolder(@NonNull AnnonceCardBinding ui) {
        super(ui.getRoot());
        this.ui = ui;
        itemView.setOnClickListener(this);
        // application = (AssosApplication) itemView.getContext().getApplicationContext();
    }

    // Ajoute les attributs d'une annonce au layout annonce_card
    public void bind(Annonce annonce, AnnonceAdapter.OnItemClickListener listener) {
        ui.titre.setText(annonce.getTitre());
        SimpleDateFormat formatter = new SimpleDateFormat("'Le' dd MMMM yyyy 'à' HH:mm", Locale.FRENCH);
        ui.datePublication.setText(formatter.format(annonce.getDatePublication().toDate()));String descriptionComplete = annonce.getDescription();
        ui.description.setText(descriptionComplete);

        if (descriptionComplete.length() > 50) { // Ou une autre limite de caractères
            ui.description.setMaxLines(1);
            ui.description.setEllipsize(TextUtils.TruncateAt.END);
            ui.pointsSuspension.setVisibility(View.VISIBLE);
            ui.nom.setText(annonce.getNomAssociation());
            ui.annonceCard.setOnClickListener(v -> {
                if (ui.description.getMaxLines() == 1) {
                    ui.description.setMaxLines(Integer.MAX_VALUE);
                    ui.description.setEllipsize(null);
                    ui.pointsSuspension.setText("▲");
                } else {
                    ui.description.setMaxLines(1);
                    ui.description.setEllipsize(TextUtils.TruncateAt.END);
                    ui.pointsSuspension.setText("▼");
                }
            });
        } else {
            ui.pointsSuspension.setVisibility(View.GONE);
        }
        //itemPos = annonce.getIdAssociation(); // Ou tout autre identifiant unique de l'annonce
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

    }
}