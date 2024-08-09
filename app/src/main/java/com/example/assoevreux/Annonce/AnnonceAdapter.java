package com.example.assoevreux.Annonce;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assoevreux.databinding.AnnonceCardBinding;

import java.util.ArrayList;
import java.util.List;

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceViewHolder> {
    private List<Annonce> liste;
    private final String TAG = "ListeAnnonces";
    private OnItemClickListener listener;
    private List<Annonce> listeFiltre;

    public AnnonceAdapter(List<Annonce> liste) {
        this.liste = liste;
        this.listeFiltre = liste;
    }

    @NonNull
    @Override
    public AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        AnnonceCardBinding binding = AnnonceCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new AnnonceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnonceViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: " + position);
        holder.bind(listeFiltre.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listeFiltre.size();
    }

    public void filter(String query) {
        if (query == null || query.isEmpty()) {
            listeFiltre = liste;
        } else {
            List<Annonce> filteredList = new ArrayList<>();
            for (Annonce annonce : liste) {
                if (annonce.getTitre().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(annonce);
                }
            }
            listeFiltre = filteredList;
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}