package com.demo.kafka;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatViewController {

    @GetMapping("/")
    public String index() {
        return "chat"; // Render chat.html
    }
}
