package com.example.websocket_study.chat.service;

import com.example.websocket_study.chat.dto.MessageRoomDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final ObjectMapper objectMapper;
    private Map<String, MessageRoomDto> msgRooms;

    @PostConstruct
    private void init() {
        msgRooms = new LinkedHashMap<>();
    }

    public List<MessageRoomDto> findAllRoom() {
        return new ArrayList<>(msgRooms.values());
    }

    public MessageRoomDto findById(String roomId) {
        return msgRooms.get(roomId);
    }

    public MessageRoomDto createRoom(String name) {
        String roomId = name;

        MessageRoomDto messageRoomDto = MessageRoomDto.builder()
                .roomId(roomId)
                .build();

        msgRooms.put(roomId, messageRoomDto);
        return messageRoomDto;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
