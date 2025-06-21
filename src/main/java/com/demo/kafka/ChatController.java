package com.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @MessageMapping("/chat")
    public void send(ChatMessage message) {
        kafkaProducerService.send("chat-topic", message.getContent());
    }
}
