package com.api.conta.services;

import com.api.conta.models.ContaModel;
import com.api.conta.repositories.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContaService {
    final ContaRepository contaRepository;

    @Transactional
    public Object save(ContaModel contaModel) { return contaRepository.save(contaModel); }

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Optional<ContaModel> findById(UUID id) { return contaRepository.findById(id); }

    public Optional<ContaModel> findByIdCliente(UUID idCliente) { return contaRepository.findByIdCliente(idCliente); }

    public List<ContaModel> findByIdGerente(UUID idGerente) { return contaRepository.findByIdGerente(idGerente); }

    public List<ContaModel> findByIdGerenteAndAtivo(UUID idGerente, Boolean ativo) {
        return contaRepository.findByIdGerenteAndAtivo(idGerente, ativo);
    }

    public boolean existsByIdCliente(UUID idCliente) { return contaRepository.existsByIdCliente(idCliente); }
}