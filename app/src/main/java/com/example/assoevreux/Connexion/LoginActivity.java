package com.example.assoevreux.Connexion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assoevreux.Application.AssosApplication;
import com.example.assoevreux.Donnee.AssosActivity;
import com.example.assoevreux.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private ActivityLoginBinding ui;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityLoginBinding.inflate(getLayoutInflater());
        // EdgeToEdge.enable(this);
        setContentView(ui.getRoot());

        editTextEmail = ui.editTextEmail;
        editTextPassword = ui.editTextPassword;
        buttonLogin = ui.buttonLogin;


        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                // Connexion avec Firebase
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Connexion réussie
                                AssosApplication application = (AssosApplication) getApplication();
                                // Récupérer l'association de l'utilisateur connecté
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("Assos").document(mAuth.getCurrentUser().getUid())
                                        .get()
                                        .addOnCompleteListener( task2 -> {
                                                    if (task2.isSuccessful()) {
                                                        String nom = task2.getResult().getString("nom");
                                                        AssosApplication.setAssociationUtilisateur(nom);
                                                        String id = task2.getResult().getString("UID");
                                                        AssosApplication.setIdAssociation(id);
                                                        String imageURL = task2.getResult().getString("image");
                                                    }
                                                });
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Connexion réussie !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, AssosActivity.class);
                                startActivity(intent);

                            } else {
                                // Échec de la connexion
                                Toast.makeText(LoginActivity.this, "Échec de la connexion.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}