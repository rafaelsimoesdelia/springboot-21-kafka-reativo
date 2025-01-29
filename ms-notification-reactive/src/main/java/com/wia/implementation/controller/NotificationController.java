package com.wia.implementation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wia.implementation.request.NotificationRequest;
import com.wia.implementation.service.NotificationService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Controlador responsável por expor endpoints para envio de notificações.
 * 
 * <p>
 * Este controlador recebe solicitações de notificações via API REST e encaminha
 * para o serviço responsável pelo processamento.
 * </p>
 *
 * @author rsdelia
 */
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    /** Serviço responsável pelo processamento de notificações. */
    private final NotificationService notificationService;

    /**
     * Endpoint para envio de notificações.
     * 
     * <p>
     * Este método recebe uma solicitação de notificação via **HTTP POST**, processa
     * a notificação de forma reativa e retorna uma resposta com status.
     * </p>
     * 
     * @param request Objeto contendo os dados da notificação (usuário, tipo e
     *                mensagem).
     * @return {@link Mono} contendo um {@link ResponseEntity} com mensagem de
     *         sucesso.
     */
    @PostMapping
    public Mono<ResponseEntity<String>> sendNotification(@RequestBody NotificationRequest request) {
	return notificationService.sendNotification(request)
		.map(result -> ResponseEntity.ok("Notificação enviada: " + result));
    }
}