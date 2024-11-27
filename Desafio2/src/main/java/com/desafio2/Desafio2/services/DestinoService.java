package com.desafio2.Desafio2.services;

import com.desafio2.Desafio2.entities.Destino;
import com.desafio2.Desafio2.entities.dtos.DestinoNewDTO;
import com.desafio2.Desafio2.repositories.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinoService {

    @Autowired
    private DestinoRepository repository;

    public Destino cadastrar(DestinoNewDTO destinoDTO) throws Exception {

        Destino destino = new Destino();
        destino.setNome(destinoDTO.getNome());
        destino.setLocalizacao(destinoDTO.getLocalizacao());
        destino.setPacoteviagem(destinoDTO.getPacoteviagem());

        repository.save(destino);

        return destino;
    }

    public Destino atualizar(Destino destino) {
        repository.save(destino);
        return destino;
    }

    public List<Destino> buscar(){
        return repository.findAll();
    }

    public Optional<Destino> buscarPorId(Long id) {
        Optional<Destino> destino = repository.findById(id);
        return destino;
    }

    public List<Destino>  buscarPorNome(String nome) {
        return repository.findByNome(nome);
    }

    public List<Destino>  buscarPorLocalizacao(String localizacao) {
        return repository.findByLocalizacao(localizacao);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }



}

