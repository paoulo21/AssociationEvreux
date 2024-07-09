package fr.iutlille.xmen;

import android.app.Application;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.*;

public class XMenApplication extends Application {

    // variable globale de l'application : la liste des XMen
    private final List<XMen> liste = new ArrayList<>();

    // initialisation du contexte
    @Override public void onCreate() {
        super.onCreate();

        // accès aux ressources
        Resources res = getResources();
        final String[] noms = res.getStringArray(R.array.noms);
        final String[] alias = res.getStringArray(R.array.alias);
        final String[] descriptions = res.getStringArray(R.array.descriptions);
        final String[] pouvoirs = res.getStringArray(R.array.pouvoirs);

        TypedArray images = res.obtainTypedArray(R.array.idimages);

        // recopier les données dans la liste
        for (int i=0; i<noms.length; ++i) {
            XMen xm = new XMen(noms[i], alias[i], descriptions[i], pouvoirs[i], images.getResourceId(i, 0));
            liste.add(xm);
        }

        // libérer certaines ressources explicitement
        images.recycle();
    }

    public List<XMen> getListe() {
        return liste;
    }
}
