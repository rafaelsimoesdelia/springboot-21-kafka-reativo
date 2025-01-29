package com.wia.implementation.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wia.implementation.request.NotificationRequest;

import lombok.RequiredArgsConstructor;

/**
 * Produtor de mensagens do Apache Kafka para envio de notificações.
 * 
 * <p>
 * Esta classe é responsável por serializar objetos de notificação e publicá-los
 * no tópico Kafka {@code notification-topic}.
 * </p>
 *
 * @author rsdelia
 */
@Service
@RequiredArgsConstructor
public class NotificationProducer {

    /** Template do Kafka para envio de mensagens. */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /** Conversor JSON para serialização da notificação. */
    private final ObjectMapper objectMapper;

    /**
     * Envia uma notificação para o tópico Kafka.
     * 
     * <p>
     * Este método serializa o objeto {@link NotificationRequest} em formato JSON e
     * o publica no tópico Kafka {@code notification-topic}.
     * </p>
     * 
     * @param request Objeto contendo os dados da notificação.
     * @throws RuntimeException Se houver erro na serialização da mensagem.
     */
    public void sendNotification(NotificationRequest request) {
	try {
	    String message = objectMapper.writeValueAsString(request);
	    kafkaTemplate.send("notification-topic", message);
	} catch (Exception e) {
	    throw new RuntimeException("Erro ao serializar mensagem", e);
	}
    }
}