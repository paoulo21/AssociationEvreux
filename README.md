# Application Mobile Associations - Ville d'Evreux

Projet d'application mobile Android native développée en Java permettant de faciliter la communication et l'accès aux informations des associations de la Ville d'Evreux.
L'application s'appuie sur une base de données cloud temps réel Google Firebase pour stocker et synchroniser les données de manière fiable.

## Fonctionnalités & Choix Techniques

- **Affichage dynamique des données** : Implémentation de `RecyclerView` avec un affichage fluide pour lister les associations et leurs détails.
- **Gestion asynchrone des données** : Intégration de **Google Firebase Realtime Database / Firestore** avec récupération et mise à jour des données en temps réel via des callbacks et listeners.
- **Architecture & Bonnes pratiques** :
  - Utilisation de `ViewBinding` pour lier de manière sécurisée et typée les composants de l'interface (UI) aux données métier.
  - Séparation des responsabilités (architecture orientée objets, découpage en packages par fonctionnalité : `Donnee`, `Association`, etc.).
  - Gestion efficace des ressources et du cycle de vie des activités (utilisation de l'`Application` class pour charger les données globalement).
- **Interface Utilisateur (UI/UX)** : Interface claire, respectueuse des guidelines Material Design pour une expérience utilisateur optimale.

## Captures d'écran

![Screenshot de l'application](https://github.com/user-attachments/assets/9f8d58db-ad71-4935-8ac9-0ab3089af191)
![Screenshot_2](https://github.com/user-attachments/assets/6fb79885-fdce-4ab1-ad52-b0a7b8e8ec84)
