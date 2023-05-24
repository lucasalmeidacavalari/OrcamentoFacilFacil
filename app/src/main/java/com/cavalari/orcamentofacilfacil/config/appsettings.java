package com.cavalari.orcamentofacilfacil.config;

import com.google.firebase.auth.FirebaseAuth;

public class appsettings {
    private static FirebaseAuth autentificacao;

    public static FirebaseAuth getFireBaseAutentificacao(){
        if (autentificacao == null){
            autentificacao = FirebaseAuth.getInstance();
        }
        return autentificacao;
    }
}
