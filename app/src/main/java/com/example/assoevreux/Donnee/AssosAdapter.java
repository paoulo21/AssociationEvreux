package com.example.assoevreux.Donnee;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assoevreux.Association.Association;
import com.example.assoevreux.databinding.AssoBinding;

import java.util.ArrayList;
import java.util.List;

public class AssosAdapter extends RecyclerView.Adapter<AssosViewHolder>{
    private List<Association> liste;
    private final String TAG = "ListeAssos";
    private OnItemClickListener listener;
    private List<Association> listeFiltre;


    public AssosAdapter(List<Association> liste){
        this.liste = liste;
        listeFiltre = liste;
    }

    @NonNull @Override
    public AssosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG,"onCreateViewHolder");

        AssoBinding ui = AssoBinding.inflate( // Assuming you have DonneeBinding
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new AssosViewHolder(ui);
    }

    @Override
    public void onBindViewHolder(@NonNull AssosViewHolder holder, int position) {
        Log.i(TAG,"onBindViewHolder: " + position);
        holder.setAssociation(listeFiltre.get(position));
        holder.setOnItemClickListener(this.listener);
    }

    @Override public int getItemCount() {
        return this.listeFiltre.size();
    }

    public void filter(String query) {
        if (query == null || query.isEmpty()) {
            listeFiltre = liste;
        } else {
            List<Association> filteredList = new ArrayList<>();
            for (Association association : liste) {
                if (association.getNom().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(association);
                }
            }listeFiltre = filteredList;
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    public void setOnItemClickListener(OnItemClickListener l) {
        this.listener = l;
    }
}
