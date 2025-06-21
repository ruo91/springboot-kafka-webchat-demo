package com.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/kafka")
public class InfoController {

    @Autowired
    private KafkaInfoService kafkaInfoService;

    @GetMapping("/info")
    public Map<String, Object> getKafkaInfo() {
        return kafkaInfoService.getKafkaInfo();
    }
}
