package com.api.conta.amqp;

import com.api.conta.controllers.ContaController;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Objects;

@RabbitListener(queues = "conta")
public class ContaReceiver {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private ContaProducer contaProducer;
    @Autowired
    private ContaController contaController;

    @RabbitHandler
    public ContaTransfer receive(@Payload ContaTransfer contaTransfer) {
        if (contaTransfer.getAction().equals("save-conta")) {
            if (Objects.isNull(contaTransfer.getContaDto())) {
                contaTransfer.setAction("failed-conta");
                return contaTransfer;
            }

            ResponseEntity<Object> response = contaController.saveConta(contaTransfer.getContaDto());

            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                contaTransfer.setAction("success-conta");
                return contaTransfer;
            }

            contaTransfer.setAction("failed-conta");
            return contaTransfer;
        }

        return null;
    }
}
