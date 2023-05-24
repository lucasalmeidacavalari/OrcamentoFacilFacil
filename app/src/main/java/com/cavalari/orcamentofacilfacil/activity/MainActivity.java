package com.cavalari.orcamentofacilfacil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cavalari.orcamentofacilfacil.R;
import com.cavalari.orcamentofacilfacil.config.appsettings;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verificaUsuarioLogado();
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorPrimaryDark)
                .fragment(R.layout.intro_1)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorPrimaryDark)
                .fragment(R.layout.intro_2)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorPrimaryDark)
                .fragment(R.layout.intro_3)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorPrimaryDark)
                .fragment(R.layout.intro_4)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorPrimaryDark)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build());

    }

    public void btnCadastrar(View view) {
        startActivity(new Intent(MainActivity.this, CadastroActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificaUsuarioLogado();
    }

    public void btnEntrar(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void verificaUsuarioLogado() {
        auth = appsettings.getFireBaseAutentificacao();
        if(auth.getCurrentUser() != null){
            goHome();
        }
    }

    public void goHome(){
        startActivity(new Intent(this, HomeActivity.class));
    }

}