package com.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.Map;

@Controller
public class PartnerController {

    @Autowired
    private MatchMakerService matchMakerService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/find")
    public void findPartner(@Payload String userId) {
        Optional<String> roomIdOpt = matchMakerService.findOrCreateRoom(userId);
        roomIdOpt.ifPresent(roomId -> {
            messagingTemplate.convertAndSend("/topic/match/" + userId, roomId);
            String partner = matchMakerService.getPartnerForUser(userId);
            if (partner != null) {
                messagingTemplate.convertAndSend("/topic/match/" + partner, roomId);
            }
        });
    }
}
