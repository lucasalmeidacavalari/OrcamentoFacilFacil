package com.cavalari.orcamentofacilfacil.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cavalari.orcamentofacilfacil.R;
import com.cavalari.orcamentofacilfacil.config.appsettings;
import com.cavalari.orcamentofacilfacil.helper.Base64Custom;
import com.cavalari.orcamentofacilfacil.model.DateUtil;
import com.cavalari.orcamentofacilfacil.model.Movimentacao;
import com.cavalari.orcamentofacilfacil.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DespesaActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference ref = appsettings.getFirebaseDataBase();
    private FirebaseAuth auth = appsettings.getFireBaseAutentificacao();
    private Double despesaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        campoData = findViewById(R.id.editData);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);
        campoValor = findViewById(R.id.editValor);

        campoData.setText(DateUtil.dataAtual());
        recuperarDespesaTotal();
    }

    public void salvaDespesa(View view) {
        if (validateDespesa()){
            movimentacao = new Movimentacao();
            movimentacao.setValor(Double.parseDouble(campoValor.getText().toString()));
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(campoData.getText().toString());
            movimentacao.setTipo("d");

            despesaTotal += movimentacao.getValor();
            atualizaDespesa();
            movimentacao.salvar();
            finish();
        }
    }

    public Boolean validateDespesa() {
        String txtData = campoData.getText().toString();
        String txtCategoria = campoCategoria.getText().toString();
        String txtDescricao = campoDescricao.getText().toString();
        String txtValor = campoValor.getText().toString();
        if (txtData.isEmpty() || txtCategoria.isEmpty() || txtDescricao.isEmpty() || txtValor.isEmpty()) {
            Toast.makeText(this, "Campos inválidos, por favor verificar os dados!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void recuperarDespesaTotal(){
        String idUsuario = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        DatabaseReference usuario = ref.child("usuarios").child(idUsuario);
        usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario1 = snapshot.getValue(Usuario.class);
                despesaTotal = usuario1.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void atualizaDespesa(){
        String idUsuario = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        DatabaseReference usuario = ref.child("usuarios").child(idUsuario);
        usuario.child("despesaTotal").setValue(despesaTotal);
    }
}