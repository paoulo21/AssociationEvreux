package com.example.assoevreux.Donnee;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.assoevreux.Association.Association;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AssosApplication extends Application {
    private static List<Association> associationList = new ArrayList<>();
    private static final String TAG = "App";
    private OnAssociationsLoadedListener listener;
    @Override public void onCreate() {
        super.onCreate();
        loadAssociations();
    }

    private void loadAssociations() {
        if (AssosApplication.associationList.isEmpty()) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                                    String categorie[] = document.contains("categorie") ? document.getString("categorie").trim().split("/") : new String[]{};;
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
                                //associationList.add(new Association("test", "test", "test","test","","asso","02323424243","test"));
                                listener.onAssociationsLoaded(associationList);
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });

        } else {
            listener.onAssociationsLoaded(associationList);
        }
    }

    public List<Association> getAssociationList() {
        return AssosApplication.associationList;
    }

    public interface OnAssociationsLoadedListener {
        void onAssociationsLoaded(List<Association> associations);
    }

    public void setOnAssociationsLoadedListener(OnAssociationsLoadedListener listener) {
        this.listener = listener;
    }


}
