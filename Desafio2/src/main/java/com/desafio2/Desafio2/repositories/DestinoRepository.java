package com.desafio2.Desafio2.repositories;

import com.desafio2.Desafio2.entities.Destino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinoRepository extends JpaRepository<Destino, Long> {
    List<Destino> findByNome(String nome);
    List<Destino> findByLocalizacao(String localizacao);
}