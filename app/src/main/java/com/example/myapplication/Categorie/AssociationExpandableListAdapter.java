package com.example.myapplication.Categorie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Association.Association;
import com.example.myapplication.Association.AssociationActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssociationExpandableListAdapter extends BaseExpandableListAdapter {

    private List<String> categories;
    private List<String> categoriesFiltre;
    private Map<String, List<Association>> associationsByCategory;
    private Context context;

    public AssociationExpandableListAdapter(Context context, Map<String, List<Association>> associationsByCategory) {
        this.categories = new ArrayList<>(associationsByCategory.keySet());
        this.categoriesFiltre = this.categories;
        this.associationsByCategory = associationsByCategory;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return categoriesFiltre.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return associationsByCategory.get(categoriesFiltre.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoriesFiltre.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return associationsByCategory.get(categoriesFiltre.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.categorie_card, parent, false);
        }

        TextView categoryTextView = convertView.findViewById(R.id.nom);
        categoryTextView.setText(categoriesFiltre.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.asso, parent, false);
        }

        Association association = associationsByCategory.get(categoriesFiltre.get(groupPosition)).get(childPosition);
        TextView associationTextView = convertView.findViewById(R.id.nom);
        associationTextView.setText(association.getNom());
        TextView presidentTextView = convertView.findViewById(R.id.president);
        presidentTextView.setText(association.getPresident());
        TextView adresseTextView = convertView.findViewById(R.id.adresse);
        adresseTextView.setText(String.valueOf(association.getAdresse()));
        ImageView imageView = convertView.findViewById(R.id.image);
        Glide.with(this.context).load(association.getImageURL()).into(imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AssociationActivity.class);
                intent.putExtra("position",association.getPosition());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void filter(String query) {
        if (query == null || query.isEmpty()) {
            categoriesFiltre = categories;
        } else {
            List<String> filteredList = new ArrayList<>();
            for (String categorie : categories) {
                if (categorie.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(categorie);
                }
            }categoriesFiltre = filteredList;
        }
        notifyDataSetChanged();
    }
}
