package com.cavalari.orcamentofacilfacil.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cavalari.orcamentofacilfacil.R;
import com.cavalari.orcamentofacilfacil.model.DateUtil;
import com.cavalari.orcamentofacilfacil.model.Movimentacao;
import com.google.android.material.textfield.TextInputEditText;

public class DespesaActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        campoData = findViewById(R.id.editData);
        campoCategoria = findViewById(R.id.editCategoria);
        campoDescricao = findViewById(R.id.editDescricao);
        campoValor = findViewById(R.id.editValor);

        campoData.setText(DateUtil.dataAtual());
    }

    public void salvaDespesa(View view) {
        if (validateDespesa()){
            movimentacao = new Movimentacao();
            movimentacao.setValor(Double.parseDouble(campoValor.getText().toString()));
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setData(campoData.getText().toString());
            movimentacao.setTipo("d");

            movimentacao.salvar();
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
}