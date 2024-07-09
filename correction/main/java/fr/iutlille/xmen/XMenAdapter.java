package fr.iutlille.xmen;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

import fr.iutlille.xmen.databinding.XMenBinding;

public class XMenAdapter extends RecyclerView.Adapter<XMenViewHolder>{
    private List<XMen> liste;
    private final String TAG = "XMEN";
    public XMenAdapter(List liste){
        this.liste = liste;
    }

    @NonNull @Override
    public XMenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG,"onCreateViewHolder");

        XMenBinding ui = XMenBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new XMenViewHolder(ui);
    }

    @Override
    public void onBindViewHolder(@NonNull XMenViewHolder holder, int position) {
        Log.i(TAG,"onBindViewHolder: " + position);
        holder.setXMen(liste.get(position));
    }

    @Override
    public int getItemCount() {
        return this.liste.size();
    }
}
