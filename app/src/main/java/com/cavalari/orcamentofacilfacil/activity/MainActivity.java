package com.cavalari.orcamentofacilfacil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cavalari.orcamentofacilfacil.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    public void btnEntrar(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

}