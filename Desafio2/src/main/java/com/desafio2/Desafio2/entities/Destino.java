package com.desafio2.Desafio2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "destino")
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "destinos_id_gen")
    @SequenceGenerator(name = "destinos_id_gen", sequenceName = "destino_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 200)
    @NotNull
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Size(max = 200)
    @NotNull
    @Column(name = "localizacao", nullable = false, length = 200)
    private String localizacao;

    @NotNull
    @Column(name = "nota", nullable = false)
    private double nota = 0.0;

    @NotNull
    @Column(name = "quantidadedenotas", nullable = false)
    private int quantidadedenotas = 0;

    @ManyToOne
    @JoinColumn(name = "pacoteviagem", nullable = true)
    @JsonIgnoreProperties("destinos")
    private PacoteViagem pacoteviagem;



    public PacoteViagem getPacoteviagem() {
        return pacoteviagem;
    }

    public void setPacoteviagem(PacoteViagem pacoteviagem) {
        this.pacoteviagem = pacoteviagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @NotNull
    public double getNota() {
        return nota;
    }

    public void setNota(@NotNull double nota) {
        this.nota = nota;
    }

    @NotNull
    public int getQuantidadedenotas() {
        return quantidadedenotas;
    }

    public void setQuantidadedenotas(@NotNull int quantidadedenotas) {
        this.quantidadedenotas = quantidadedenotas;
    }
}
