package com.demo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final SimpMessagingTemplate messagingTemplate;

    public KafkaConsumerService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void listen(String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFrom(""); // No prefix
        chatMessage.setContent(message); // Send full message content as-is
        messagingTemplate.convertAndSend("/topic/messages", chatMessage);
    }
}
