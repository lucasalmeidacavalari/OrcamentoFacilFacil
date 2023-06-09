package com.cavalari.orcamentofacilfacil.model;

import com.cavalari.orcamentofacilfacil.config.appsettings;
import com.cavalari.orcamentofacilfacil.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimentacao {
    private String movimentacaoId;
    private String data;
    private String categoria;
    private String descricao;
    private String tipo;
    private double valor;

    public Movimentacao() {
    }

    public void salvar() {
        //FireBase
        DatabaseReference ref = appsettings.getFirebaseDataBase();
        FirebaseAuth auth = appsettings.getFireBaseAutentificacao();
        //CodIdUsuario
        String idUsuario = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
        //FormatandoData
        String dataFormat = DateUtil.mesAno(data);
        ref.child("movimentacao")
                .child(idUsuario)
                .child(dataFormat)
                .push()
                .setValue(this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getMovimentacaoId() {
        return movimentacaoId;
    }

    public void setMovimentacaoId(String movimentacaoId) {
        this.movimentacaoId = movimentacaoId;
    }
}
