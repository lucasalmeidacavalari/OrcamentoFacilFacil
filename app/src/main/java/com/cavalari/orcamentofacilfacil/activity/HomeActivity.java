package com.cavalari.orcamentofacilfacil.activity;

import android.content.Intent;
import android.os.Bundle;

import com.cavalari.orcamentofacilfacil.databinding.ActivityHomeBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;


import com.cavalari.orcamentofacilfacil.R;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public void adicionarDespesas(View view) {
        startActivity(new Intent(this, DespesaActivity.class));
    }

    public void adicionarReceitas(View view) {
        startActivity(new Intent(this, ReceitasActivity.class));
    }
}