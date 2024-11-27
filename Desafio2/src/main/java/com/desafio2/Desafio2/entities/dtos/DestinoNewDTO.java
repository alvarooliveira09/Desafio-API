package com.desafio2.Desafio2.entities.dtos;

import com.desafio2.Desafio2.entities.PacoteViagem;
import java.io.Serializable;

public class DestinoNewDTO implements Serializable {

    private String nome;

    private String localizacao;

    private PacoteViagem pacoteviagem;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public PacoteViagem getPacoteviagem() {
        return pacoteviagem;
    }

    public void setPacoteviagem(PacoteViagem pacoteviagem) {
        this.pacoteviagem = pacoteviagem;
    }
}
