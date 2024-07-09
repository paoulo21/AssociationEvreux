package com.example.myapplication.Donnee;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Association.Association;
import com.example.myapplication.databinding.AssoBinding;

import java.util.List;

public class AssosAdapter extends RecyclerView.Adapter<AssosViewHolder>{
    private List<Association> liste;
    private final String TAG = "ListeAssos";
    private OnItemClickListener listener;


    public AssosAdapter(List<Association> liste){
        this.liste = liste;
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
        holder.setAssociation(liste.get(position));
        holder.setOnItemClickListener(this.listener);
    }

    @Override public int getItemCount() {
        return this.liste.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    public void setOnItemClickListener(OnItemClickListener l) {
        this.listener = l;
    }
}
