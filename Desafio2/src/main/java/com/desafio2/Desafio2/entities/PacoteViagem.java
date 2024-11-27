package com.desafio2.Desafio2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pacoteviagem")
public class PacoteViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pacotesviagem_id_gen")
    @SequenceGenerator(name = "pacotesviagem_id_gen", sequenceName = "pacoteviagem_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 200)
    @NotNull
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @OneToMany(mappedBy = "pacoteviagem")
    @JsonIgnoreProperties("pacoteviagem")
    private List<Destino> destinos;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(max = 200) @NotNull String getNome() {
        return nome;
    }

    public void setNome(@Size(max = 200) @NotNull String nome) {
        this.nome = nome;
    }

    public List<Destino> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<Destino> destinos) {
        this.destinos = destinos;
    }
}
