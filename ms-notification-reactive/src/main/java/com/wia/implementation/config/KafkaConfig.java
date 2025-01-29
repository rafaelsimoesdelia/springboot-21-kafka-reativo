package com.wia.implementation.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;

import reactor.kafka.receiver.ReceiverOptions;

/**
 * Configuração do Apache Kafka para o microserviço de notificações.
 * 
 * Esta classe define os beans necessários para a produção e o consumo de
 * mensagens no Kafka, incluindo suporte para consumidores reativos.
 * 
 * @author rsdelia
 */
@EnableKafka
@Configuration
public class KafkaConfig {

    /**
     * Configuração da fábrica de produtores do Kafka.
     * 
     * @return uma {@link ProducerFactory} configurada para serializar mensagens
     *         como {@link String}.
     */
    @Bean
    ProducerFactory<String, String> producerFactory() {
	Map<String, Object> config = new HashMap<>();
	config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Configuração do template do Kafka para envio de mensagens.
     * 
     * @return um {@link KafkaTemplate} configurado com a {@link ProducerFactory}.
     */
    @Bean
    KafkaTemplate<String, String> kafkaTemplate() {
	return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Configuração do consumidor reativo do Kafka.
     * 
     * @return um {@link ReactiveKafkaConsumerTemplate} para processamento de
     *         mensagens de forma reativa.
     */
    @Bean
    public ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate() {
	Map<String, Object> props = new HashMap<>();
	props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
	props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

	ReceiverOptions<String, String> receiverOptions = ReceiverOptions.<String, String>create(props)
		.subscription(Collections.singleton("notification-topic"));

	return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
    }

    /**
     * Configuração da fábrica de consumidores do Kafka.
     * 
     * Define os parâmetros para deserialização, offset e quantidade de mensagens a
     * serem processadas por lote.
     * 
     * @return uma {@link ConsumerFactory} configurada para processar mensagens do
     *         Kafka.
     */
    @Bean
    ConsumerFactory<String, String> consumerFactory() {
	Map<String, Object> config = new HashMap<>();
	config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
	config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
	config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50); // Lote de 50 mensagens
	config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	return new DefaultKafkaConsumerFactory<>(config);
    }

    /**
     * Configuração da fábrica de listeners do Kafka para consumo de mensagens em
     * lote.
     * 
     * @return um {@link KafkaListenerContainerFactory} configurado para processar
     *         mensagens em lote.
     */
    @Bean
    KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactory());
	factory.setBatchListener(true); // Processa mensagens em lote
	return factory;
    }
}