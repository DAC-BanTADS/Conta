package com.api.conta.repositories;

import com.api.conta.models.ContaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaRepository extends JpaRepository<ContaModel, UUID> {
    Optional<ContaModel> findByIdCliente(UUID idCliente);

    List<ContaModel> findByIdGerente(UUID idGerente);

    List<ContaModel> findByIdGerenteAndAtivo(UUID idGerente, Boolean ativo);

    boolean existsByIdCliente(UUID idCliente);
}
