package com.example.assoevreux.Annonce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assoevreux.Application.AssosApplication;
import com.example.assoevreux.databinding.ActivityPostAnnonceBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PostAnnonceActivity extends AppCompatActivity {

    private EditText editTextTitre, editTextDescription;
    private Button buttonPoster;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ActivityPostAnnonceBinding ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityPostAnnonceBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        editTextTitre = ui.editTextTitre;
        editTextDescription = ui.editTextDescription;
        buttonPoster = ui.buttonPoster;

        // Initialiser Firebase Auth et Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        buttonPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser == null) {
                    // Rediriger l'utilisateur vers la page de connexion
                    Toast.makeText(PostAnnonceActivity.this, "Vous devez être connecté pour poster une annonce.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String titre = editTextTitre.getText().toString();
                String description = editTextDescription.getText().toString();

                if (titre.isEmpty() || description.isEmpty() /* || ... autres champs à vérifier */) {
                    Toast.makeText(PostAnnonceActivity.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                    return; // Arrêter le processus de postage
                }

                //Créer un Map pour les données de l'annonce
                Map<String, Object> annonceData = new HashMap<>();
                annonceData.put("titre", titre);
                annonceData.put("description", description);
                annonceData.put("datePublication", Timestamp.now());
                annonceData.put("idAssociation", AssosApplication.getIdAssociation()); // Associer l'annonce à l'association connectée

                // Ajouter l'annonce à Firestore
                db.collection("Annonces")
                        .add(annonceData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Annonce ajoutée avec succès
                                Toast.makeText(PostAnnonceActivity.this, "Annonce postée avec succès !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PostAnnonceActivity.this, AnnonceActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Erreur lors de l'ajout de l'annonce
                                Toast.makeText(PostAnnonceActivity.this, "Erreur lors du postage de l'annonce.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}