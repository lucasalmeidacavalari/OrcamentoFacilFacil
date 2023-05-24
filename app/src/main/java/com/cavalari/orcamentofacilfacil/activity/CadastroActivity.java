package com.cavalari.orcamentofacilfacil.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cavalari.orcamentofacilfacil.R;
import com.cavalari.orcamentofacilfacil.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.btnCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtNome = campoNome.getText().toString();
                String txtEmail = campoEmail.getText().toString();
                String txtSenha = campoSenha.getText().toString();

                // Validação

                if (!txtNome.isEmpty() && !txtEmail.isEmpty() && !txtSenha.isEmpty()) {
                    usuario = new Usuario();
                    usuario.setEmail(txtEmail);
                    usuario.setNome(txtNome);
                    usuario.setSenha(txtSenha);
                    cadastrarUsuario();
                } else {
                    Toast.makeText(CadastroActivity.this, "Campos inválidos, por favor verificar os dados!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void cadastrarUsuario() {}
}
