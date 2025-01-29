package com.wia.implementation.consumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wia.implementation.request.NotificationRequest;
import com.wia.implementation.service.NotificationService;

import lombok.RequiredArgsConstructor;

/**
 * Consumidor de mensagens do Apache Kafka para o sistema de notificações.
 * 
 * <p>
 * Esta classe escuta mensagens do tópico Kafka {@code notification-topic},
 * processa as notificações e lida com possíveis falhas, enviando mensagens com
 * erro para um Dead Letter Topic.
 * </p>
 *
 * @author rsdelia
 */
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    /** Serviço de processamento de notificações. */
    private final NotificationService notificationService;

    /** Template do Kafka para envio de mensagens. */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /** Conversor JSON para objetos Java. */
    private final ObjectMapper objectMapper;

    /**
     * Método que consome mensagens do Kafka em lote.
     *
     * <p>
     * Este método processa mensagens do tópico {@code notification-topic},
     * deserializa os dados e encaminha para o {@link NotificationService}. Em caso
     * de erro, as mensagens são enviadas para um Dead Letter Topic.
     * </p>
     *
     * @param records Lista de mensagens recebidas do Kafka.
     */
    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void consumeBatch(List<String> records) {
	List<String> failedMessages = new ArrayList<>();

	for (String message : records) {
	    try {
		// Converte a mensagem JSON para um objeto NotificationRequest
		NotificationRequest request = objectMapper.readValue(message, NotificationRequest.class);
		notificationService.processNotification(request);
	    } catch (Exception e) {
		// Adiciona a mensagem com erro à lista de falhas
		failedMessages.add(message);
	    }
	}

	// Reenvia mensagens que falharam para o Dead Letter Topic
	failedMessages.forEach(msg -> kafkaTemplate.send("dead-letter-topic", msg));
    }
}