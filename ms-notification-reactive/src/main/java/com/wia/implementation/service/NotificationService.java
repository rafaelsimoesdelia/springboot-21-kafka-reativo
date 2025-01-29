package com.wia.implementation.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.wia.implementation.request.NotificationRequest;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;
    private final SmsService smsService;
    private final PushService pushService;

    public Mono<String> sendNotification(NotificationRequest request) {
	return Mono.defer(() -> {
	    processNotification(request); // Chama o método síncrono
	    return Mono.just("Enviado para Kafka"); // Retorna após execução
	});
    }

    @Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
    public void processNotification(NotificationRequest request) {
	switch (request.getType().toUpperCase()) {
	case "EMAIL":
	    emailService.sendEmail(request.getUserId(), request.getMessage());
	    break;
	case "SMS":
	    smsService.sendSms(request.getUserId(), request.getMessage());
	    break;
	case "PUSH":
	    pushService.sendPush(request.getUserId(), request.getMessage());
	    break;
	default:
	    throw new IllegalArgumentException("Tipo de notificação inválido");
	}
    }
}