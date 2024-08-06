package com.example.assoevreux.Donnee;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assoevreux.Association.Association;
import com.example.assoevreux.Association.AssociationActivity;
import com.example.assoevreux.databinding.AssoBinding;

public class AssosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private final AssoBinding ui;
    private AssosAdapter.OnItemClickListener listener;
    AssosApplication application;
    public int itemPos;

    public AssosViewHolder(@NonNull AssoBinding ui)
    {
        super(ui.getRoot());
        this.ui = ui;
        itemView.setOnClickListener(this);
        application = (AssosApplication) itemView.getContext().getApplicationContext();
    }
    // Ajoute les attributs d'une association a un layout asso
    public void setAssociation(Association association) {
        ui.nom.setText(association.getNom());
        ui.president.setText(association.getPresident());
        ui.adresse.setText(String.valueOf(association.getAdresse()));
        Glide.with(itemView.getContext()).load(association.getImageURL()).into(ui.image);
        itemPos = association.getPosition();
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onItemClick(getAbsoluteAdapterPosition());
        Intent intent = new Intent(itemView.getContext(), AssociationActivity.class);
        intent.putExtra("position",itemPos);
        itemView.getContext().startActivity(intent);
    }
    public void setOnItemClickListener(AssosAdapter.OnItemClickListener l) {
        this.listener = l;
    }



}
