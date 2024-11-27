package com.desafio2.Desafio2.services;

import com.desafio2.Desafio2.entities.Destino;
import com.desafio2.Desafio2.entities.PacoteViagem;
import com.desafio2.Desafio2.entities.dtos.DestinoNewDTO;
import com.desafio2.Desafio2.entities.dtos.PacoteViagemNewDTO;
import com.desafio2.Desafio2.repositories.DestinoRepository;
import com.desafio2.Desafio2.repositories.PacoteViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacoteViagemService {

    @Autowired
    private PacoteViagemRepository repository;

    public PacoteViagem cadastrar(PacoteViagemNewDTO pacoteViagemNewDTO) throws Exception {

        PacoteViagem pacoteViagem = new PacoteViagem();
        pacoteViagem.setNome(pacoteViagemNewDTO.getNome());

        return repository.save(pacoteViagem);

    }

    public PacoteViagem atualizar(PacoteViagem pacoteviagem) {
        return repository.save(pacoteviagem);
    }

    public List<PacoteViagem> buscar(){
        return repository.findAll();
    }

    public Optional<PacoteViagem> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }



}

