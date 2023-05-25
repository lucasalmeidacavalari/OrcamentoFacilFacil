package com.cavalari.orcamentofacilfacil.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.cavalari.orcamentofacilfacil.adapter.AdapterMovimentacao;
import com.cavalari.orcamentofacilfacil.config.appsettings;
import com.cavalari.orcamentofacilfacil.databinding.ActivityHomeBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.cavalari.orcamentofacilfacil.R;
import com.cavalari.orcamentofacilfacil.helper.Base64Custom;
import com.cavalari.orcamentofacilfacil.model.Movimentacao;
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
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private MaterialCalendarView calendarView;
    private TextView textSaudacao, textSaldo;
    private RecyclerView recyclerView;
    private Double despesaTotal = 0.0;
    private Double receitaTotal = 0.0;
    private Double resumoUsuario = 0.0;
    private String mesAno;
    private List<Movimentacao> movimentacaos = new ArrayList<>();
    private Movimentacao movimentacao;
    private FirebaseAuth auth = appsettings.getFireBaseAutentificacao();
    private DatabaseReference ref = appsettings.getFirebaseDataBase();
    private DatabaseReference moveRef;
    private DatabaseReference usuarioref;
    private ValueEventListener valueEventListenerUsuario;
    private ValueEventListener valueEventListenerMovimentacoes;
    private AdapterMovimentacao adapterMovimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //ActionBarControle
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        //Adapter
        adapterMovimentacao = new AdapterMovimentacao(movimentacaos, this);

        //Instancias
        calendarView = findViewById(R.id.calendarView);
        textSaldo = findViewById(R.id.textSaldo);
        textSaudacao = findViewById(R.id.textSaudacao);
        recyclerView = findViewById(R.id.recyclerMovimentos);
        configureCalendarView();
        swipe();

        //Recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterMovimentacao);
    }

    @Override
    protected void onStart() {
        recureResumo();
        recupereMovimentacoes();
        super.onStart();
    }

    @Override
    protected void onStop() {
        usuarioref.removeEventListener(valueEventListenerUsuario);
        moveRef.removeEventListener(valueEventListenerMovimentacoes);
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
        CalendarDay day = calendarView.getCurrentDate();
        String mes = String.format("%02d", day.getMonth());
        mesAno = String.valueOf(mes + "" + day.getYear());
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String mes = String.format("%02d", date.getMonth());
                mesAno = String.valueOf(mes + "" + date.getYear());
                moveRef.removeEventListener(valueEventListenerMovimentacoes);
                recupereMovimentacoes();
            }
        });
    }

    public void recureResumo() {
        String idUsuario = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        usuarioref = ref.child("usuarios").child(idUsuario);
        valueEventListenerUsuario = usuarioref.addValueEventListener(new ValueEventListener() {
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

    public void recupereMovimentacoes() {
        String idUsuario = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        moveRef = ref.child("movimentacao")
                .child(idUsuario)
                .child(mesAno);

        valueEventListenerMovimentacoes = moveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movimentacaos.clear();
                for (DataSnapshot dados : snapshot.getChildren()) {
                    Movimentacao movimentacao = dados.getValue(Movimentacao.class);
                    movimentacao.setMovimentacaoId(dados.getKey());
                    movimentacaos.add(movimentacao);
                }
                adapterMovimentacao.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void swipe() {
        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirMovimentacao(viewHolder);
            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);
    }

    public void excluirMovimentacao(@NonNull RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir Movimentação");
        builder.setMessage("Você tem certeza que deseja excluir?");
        builder.setCancelable(false);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                movimentacao = movimentacaos.get(position);
                String idUsuario = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
                moveRef = ref.child("movimentacao")
                        .child(idUsuario)
                        .child(mesAno);
                moveRef.child(movimentacao.getMovimentacaoId()).removeValue();
                adapterMovimentacao.notifyItemRemoved(position);
                atualizaSaldo();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapterMovimentacao.notifyDataSetChanged();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void atualizaSaldo(){

    }
}
