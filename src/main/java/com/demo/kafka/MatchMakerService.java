package com.demo.kafka;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MatchMakerService {
    private final Queue<String> waitingUsers = new ConcurrentLinkedQueue<>();
    private final Map<String, String> userRoomMap = new HashMap<>();

    public synchronized Optional<String> findOrCreateRoom(String userId) {
        if (!waitingUsers.isEmpty()) {
            String peer = waitingUsers.poll();
            String roomId = UUID.randomUUID().toString();
            userRoomMap.put(userId, roomId);
            userRoomMap.put(peer, roomId);
            return Optional.of(roomId);
        } else {
            waitingUsers.add(userId);
            return Optional.empty();
        }
    }

    public String getRoomForUser(String userId) {
        return userRoomMap.get(userId);
    }

    public String getPartnerForUser(String currentUserId) {
        String roomId = userRoomMap.get(currentUserId);
        if (roomId == null) return null;
        return userRoomMap.entrySet().stream()
                .filter(e -> !e.getKey().equals(currentUserId) && e.getValue().equals(roomId))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}
