package com.cavalari.orcamentofacilfacil.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class appsettings {
    private static FirebaseAuth autentificacao;
    private static DatabaseReference firebase;

    public static FirebaseAuth getFireBaseAutentificacao(){
        if (autentificacao == null){
            autentificacao = FirebaseAuth.getInstance();
        }
        return autentificacao;
    }

    public static DatabaseReference getFirebaseDataBase(){
        if (firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }
}
