package com.example.assoevreux.Donnee;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.assoevreux.Annonce.Annonce;
import com.example.assoevreux.Association.Association;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AssosApplication extends Application {
    private static List<Association> associationList = new ArrayList<>();
    private static List<Annonce> annonceList = new ArrayList<>();
    private static final String TAG = "App";
    private OnAssociationsLoadedListener listener;
    @Override public void onCreate() {
        super.onCreate();
        loadAssociations();
        }

    private void loadAssociations() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (AssosApplication.associationList.isEmpty()) {
            //requete firebase pour charger les associations
            db.collection("Assos")
                    .orderBy("nom")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            int position = 0;
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String nom = document.contains("nom") ? document.getString("nom") : "";
                                    String president = document.contains("president") ? document.getString("president") : "";
                                    String adresse = document.contains("adresse") ? document.getString("adresse") : "";
                                    String description = document.contains("description") ? document.getString("description") : "";
                                    String imageURL = document.contains("image") ? document.getString("image") : "";
                                    String categorie[] = document.contains("categorie") ? document.getString("categorie").trim().split("/") : new String[]{};
                                    for (int i = 0; i < categorie.length; i++) {categorie[i] = "\n• " + categorie[i];
                                    }
                                    String telephone = document.contains("telephone") ? document.getString("telephone") : "";
                                    String email = document.contains("email") ? document.getString("email") : "";
                                    String action = document.contains("actions") ? document.getString("actions") : "";
                                    String publicCible = document.contains("public") ? document.getString("public") : "";
                                    String territoire = document.contains("territoire d'intervention") ? document.getString("territoire d'intervention") : "";
                                    String siteWeb = document.contains("site web") ? document.getString("site web") : "";
                                    Association asso = new Association(nom, president, adresse,description,imageURL,categorie,telephone,email,action,publicCible,territoire,siteWeb,position);
                                    position++;
                                    associationList.add(asso);
                                }
                                listener.onAssociationsLoaded(associationList);
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });

        } else {
            // Si la liste d'associations est déjà chargée, notifie directement le listener
            listener.onAssociationsLoaded(associationList);
        }
        // Récupérer les annonces
        db.collection("Annonces")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String idAssociation = document.getString("idAssociation");

                            // Récupérer le nom de l'association
                            db.collection("Assos").document(idAssociation)
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            DocumentSnapshot associationDoc = task2.getResult();
                                            String nomAssociation = associationDoc.getString("nom");
                                            Annonce annonce = new Annonce(document.getString("titre"), document.getString("description"), document.getTimestamp("datePublication"), nomAssociation);
                                            annonceList.add(annonce);
                                        } else {
                                            Log.w(TAG, "Erreur lors de la récupération du nom de l'association", task2.getException());
                                        }

                                    });
                        }
                    }else{
                        // Gérer l'erreur
                        Log.w(TAG, "Erreur lors de la récupération des annonces", task.getException());
                    }});
        annonceList.add(new Annonce("titre1","description1", Timestamp.now(),"451"));
        annonceList.add(new Annonce("titre2","description2",Timestamp.now(),"165"));
        annonceList.add(new Annonce("titre3","descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription",Timestamp.now(),"1"));

    }

    public List<Association> getAssociationList() {
        return AssosApplication.associationList;
    }

    public List<Annonce> getAnnonceList() {
        return annonceList;
    }

    public interface OnAssociationsLoadedListener {
        void onAssociationsLoaded(List<Association> associations);
    }

    public void setOnAssociationsLoadedListener(OnAssociationsLoadedListener listener) {
        this.listener = listener;
    }


}
