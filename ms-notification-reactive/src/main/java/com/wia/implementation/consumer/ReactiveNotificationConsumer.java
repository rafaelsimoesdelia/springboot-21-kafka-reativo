package com.wia.implementation.consumer;

import java.time.Duration;
import java.util.List;

import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Consumidor reativo de mensagens do Apache Kafka para o sistema de
 * notificações.
 * 
 * <p>
 * Esta classe utiliza programação reativa para consumir mensagens do Kafka,
 * processá-las em lotes e garantir um processamento eficiente.
 * </p>
 *
 * @author rsdelia
 */
@Service
@RequiredArgsConstructor
public class ReactiveNotificationConsumer {

    /** Template reativo do Kafka para consumo de mensagens. */
    private final ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate;

    /**
     * Inicia o consumo de mensagens do Kafka assim que a aplicação sobe.
     * 
     * <p>
     * Este método é chamado automaticamente pelo Spring após a criação do bean,
     * utilizando a anotação {@link PostConstruct}. Ele recebe mensagens do Kafka de
     * forma reativa, agrupa em lotes de até 50 mensagens ou por um tempo máximo de
     * 2 segundos e então as envia para processamento.
     * </p>
     */
    @PostConstruct
    public void startConsuming() {
	reactiveKafkaConsumerTemplate.receive().map(record -> record.value()) // Extrai o valor da mensagem
		.bufferTimeout(50, Duration.ofSeconds(2)) // Processa no máximo 50 mensagens ou a cada 2 segundos
		.flatMap(this::processBatch) // Processa o lote de mensagens
		.subscribe();
    }

    /**
     * Processa um lote de notificações recebidas do Kafka.
     * 
     * <p>
     * Este método recebe uma lista de notificações e as imprime no console. Pode
     * ser expandido para incluir lógica de processamento real.
     * </p>
     * 
     * @param notifications Lista de mensagens recebidas do Kafka.
     * @return {@link Mono} vazio indicando que o processamento foi concluído.
     */
    private Mono<Void> processBatch(List<String> notifications) {
	notifications.forEach(msg -> System.out.println("Processado: " + msg));
	return Mono.empty();
    }
}