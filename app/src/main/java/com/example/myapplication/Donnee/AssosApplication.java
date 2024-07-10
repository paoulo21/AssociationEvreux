package com.example.myapplication.Donnee;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.Association.Association;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AssosApplication extends Application {
    private List<Association> associationList = new ArrayList<>();
    private static final String TAG = "App";
    private OnAssociationsLoadedListener listener;
    private int positionAsso;
    @Override public void onCreate() {
        super.onCreate();
        if (associationList.isEmpty()) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Assos")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Association asso = new Association(document.getString("nom"), document.getString("president"), document.getString("adresse"));
                                    associationList.add(asso);
                                }
                                associationList.add(new Association("test", "test", "test"));
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

    public int getPositionAsso() {
        return positionAsso;
    }

    public void setPositionAsso(int positionAsso) {
        this.positionAsso = positionAsso;
    }

    public List<Association> getAssociationList() {
        return associationList;
    }

    public interface OnAssociationsLoadedListener {
        void onAssociationsLoaded(List<Association> associations);
    }

    public void setOnAssociationsLoadedListener(OnAssociationsLoadedListener listener) {
        this.listener = listener;
    }


}
