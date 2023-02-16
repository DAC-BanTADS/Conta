package com.api.conta.amqp;

import com.api.conta.dtos.ContaDto;
import com.api.conta.models.ContaModel;
import com.api.conta.services.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class ContaHelper {
    final ContaService contaService;

    public ContaHelper(ContaService contaService) {
        this.contaService = contaService;
    }

    public ResponseEntity<Object> saveConta(@Valid ContaDto contaDto){
        if(contaService.existsByIdCliente(contaDto.getIdCliente())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Este cliente já possui uma conta!");
        }

        var contaModel = new ContaModel();
        BeanUtils.copyProperties(contaDto, contaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.save(contaModel));
    }
}
