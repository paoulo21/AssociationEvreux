package fr.iutlille.xmen;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.iutlille.xmen.databinding.XMenBinding;

public class XMenViewHolder extends RecyclerView.ViewHolder {
    private final XMenBinding ui;
    public XMenViewHolder(@NonNull XMenBinding ui)
    {
        super(ui.getRoot());
        this.ui = ui;
    }

    public void setXMen(XMen xmen) {
        ui.nom.setText(xmen.getNom());
        ui.alias.setText(xmen.getAlias());
        ui.pouvoirs.setText(xmen.getPouvoirs());
        ui.description.setText(xmen.getDescription());
        ui.image.setImageResource(xmen.getIdImage());
    }
}
