package com.cavalari.orcamentofacilfacil.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cavalari.orcamentofacilfacil.R;
import com.cavalari.orcamentofacilfacil.config.appsettings;
import com.cavalari.orcamentofacilfacil.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;


public class LoginActivity extends AppCompatActivity {
    private EditText campoEmail, campoSenha;
    private Button btEntrar;
    private Usuario usuario;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.edtEmail);
        campoSenha = findViewById(R.id.edtSenha);
        btEntrar = findViewById(R.id.btnEntrar);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = campoEmail.getText().toString();
                String txtSenha = campoSenha.getText().toString();

                // Validação

                if ( !txtEmail.isEmpty() && !txtSenha.isEmpty()) {
                    usuario = new Usuario();
                    usuario.setEmail(txtEmail);
                    usuario.setSenha(txtSenha);
                    validaLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "Campos inválidos, por favor verificar os dados!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validaLogin(){
        auth = appsettings.getFireBaseAutentificacao();
        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    goHome();
                }else{
                    String excessao;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        excessao = "Dados estão invalidos!";
                    }catch (FirebaseAuthInvalidUserException e){
                        excessao = "Usuário não está cadastrado!";
                    } catch (Exception e){
                        excessao = "Erro ao acessar o usuário: "+e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, excessao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void goHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}