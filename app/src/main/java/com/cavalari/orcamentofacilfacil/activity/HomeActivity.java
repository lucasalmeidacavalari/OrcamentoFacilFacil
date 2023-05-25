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
import com.cavalari.orcamentofacilfacil.helper.Base64Custom;
import com.cavalari.orcamentofacilfacil.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private MaterialCalendarView calendarView;
    private TextView textSaudacao, textSaldo;
    private Double despesaTotal = 0.0;
    private Double receitaTotal = 0.0;
    private Double resumoUsuario = 0.0;
    private FirebaseAuth auth = appsettings.getFireBaseAutentificacao();
    private DatabaseReference ref = appsettings.getFirebaseDataBase();
    private DatabaseReference usuarioref;
    private ValueEventListener valueEventListener;

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

    @Override
    protected void onStart() {
        recuperarResumo();
        super.onStart();
    }

    @Override
    protected void onStop() {
        usuarioref.removeEventListener(valueEventListener);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuSair) {
            auth.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void adicionarDespesas(View view) {
        startActivity(new Intent(this, DespesaActivity.class));
    }

    public void adicionarReceitas(View view) {
        startActivity(new Intent(this, ReceitasActivity.class));
    }

    public void configureCalendarView() {
        CharSequence meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendarView.setTitleMonths(meses);
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });
    }

    public void recuperarResumo() {
        String idUsuario = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        usuarioref = ref.child("usuarios").child(idUsuario);
        valueEventListener = usuarioref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario1 = snapshot.getValue(Usuario.class);
                despesaTotal = usuario1.getDespesaTotal();
                receitaTotal = usuario1.getReceitaTotal();
                resumoUsuario = receitaTotal - despesaTotal;

                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String resultFormart = decimalFormat.format(resumoUsuario);

                textSaudacao.setText("Olá, " + usuario1.getNome());
                textSaldo.setText("R$ " + resultFormart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}