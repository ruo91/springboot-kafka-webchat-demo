package com.demo.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Service
public class KafkaProducerService {

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, String content) {
        long start = System.nanoTime();
        kafkaTemplate.send(topic, content);
    }

    private String generateProcessingTime(long start) {
        long durationNano = System.nanoTime() - start;
        double durationMillis = durationNano / 1_000_000.0;
        return String.format(" (Kafka processing time: %.3f ms (%d us))", durationMillis, durationNano / 1000);
    }
}
