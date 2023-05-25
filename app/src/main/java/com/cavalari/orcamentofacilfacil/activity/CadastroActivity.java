package com.cavalari.orcamentofacilfacil.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cavalari.orcamentofacilfacil.R;
import com.cavalari.orcamentofacilfacil.config.appsettings;
import com.cavalari.orcamentofacilfacil.helper.Base64Custom;
import com.cavalari.orcamentofacilfacil.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private Usuario usuario;
    private FirebaseAuth autentificacao;

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


    private void cadastrarUsuario() {
        autentificacao = appsettings.getFireBaseAutentificacao();
        autentificacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.Salvar();
                    finish();
                }else{
                    String excessao;
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excessao = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        excessao = "Por favor, digite um e-mail válido!";
                    } catch (FirebaseAuthUserCollisionException e){
                        excessao = "Este e-mail já foi cadastrado!";
                    } catch (Exception e){
                        excessao = "Erro ao cadastrar usuário: "+e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, excessao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
