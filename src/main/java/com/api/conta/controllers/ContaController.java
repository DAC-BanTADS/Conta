package com.api.conta.controllers;

import com.api.conta.dtos.ContaDto;
import com.api.conta.models.ContaModel;
import com.api.conta.services.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/conta")
public class ContaController {
    final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    public ResponseEntity<Object> saveConta(@RequestBody @Valid ContaDto contaDto){
        if(contaService.existsByIdCliente(contaDto.getIdCliente())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Este cliente já possui uma conta!");
        }

        var contaModel = new ContaModel();
        BeanUtils.copyProperties(contaDto, contaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.save(contaModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneConta(@PathVariable(value = "id") UUID id){
        Optional<ContaModel> contaModelOptional = contaService.findById(id);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(contaModelOptional.get());
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<Object> getContaByIdCliente(@PathVariable(value = "idCliente") UUID idCliente){
        Optional<ContaModel> contaModelOptional = contaService.findByIdCliente(idCliente);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(contaModelOptional.get());
    }

    @GetMapping("/gerente/{idGerente}")
    public ResponseEntity<Object> getContaByIdGerente(
            @PathVariable(value = "idGerente") UUID idGerente,
            @RequestParam(value = "ativo", required = false) Boolean ativo
    ){
        List<ContaModel> contaModelList;

        if (ativo != null) {
            contaModelList = contaService.findByIdGerenteAndAtivo(idGerente, ativo);
        } else {
            contaModelList = contaService.findByIdGerente(idGerente);
        }

        if (contaModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma conta encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(contaModelList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateConta(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ContaDto contaDto){
        Optional<ContaModel> contaModelOptional = contaService.findById(id);
        if (!contaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }
        var contaModel = new ContaModel();
        BeanUtils.copyProperties(contaDto, contaModel);
        contaModel.setId(contaModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(contaService.save(contaModel));
    }
}
