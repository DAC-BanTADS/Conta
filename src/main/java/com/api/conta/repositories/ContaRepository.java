package com.api.conta.repositories;

import com.api.conta.models.ContaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "select * from conta where id_gerente=?1 order by saldo desc limit 5;", nativeQuery = true)
    List<ContaModel> findByIdGerenteMelhores(UUID idGerente);
}
