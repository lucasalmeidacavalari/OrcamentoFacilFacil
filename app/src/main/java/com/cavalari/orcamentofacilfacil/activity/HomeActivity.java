package com.cavalari.orcamentofacilfacil.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.cavalari.orcamentofacilfacil.config.appsettings;
import com.cavalari.orcamentofacilfacil.databinding.ActivityHomeBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;



import com.cavalari.orcamentofacilfacil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private MaterialCalendarView calendarView;
    private TextView textSaudacao, textSaldo;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //ActionBarControle
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        //Code
        calendarView = findViewById(R.id.calendarView);
        textSaldo = findViewById(R.id.textSaldo);
        textSaudacao = findViewById(R.id.textSaudacao);
        configureCalendarView();
    }

    public void adicionarDespesas(View view) {
        startActivity(new Intent(this, DespesaActivity.class));
    }

    public void adicionarReceitas(View view) {
        startActivity(new Intent(this, ReceitasActivity.class));
    }

    public void configureCalendarView(){
        CharSequence meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendarView.setTitleMonths( meses );
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuSair){
            auth = appsettings.getFireBaseAutentificacao();
            auth.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}