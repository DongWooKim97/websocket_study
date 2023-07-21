package com.example.websocket_study.chat.handler;

import com.example.websocket_study.chat.dto.MessageDto;
import com.example.websocket_study.chat.dto.MessageRoomDto;
import com.example.websocket_study.chat.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        log.info("payload : {}", payload);

        MessageDto messageDto = objectMapper.readValue(payload, MessageDto.class);
        MessageRoomDto messageRoomDto = messageService.findById(messageDto.getRoomId());
        messageRoomDto.handleActions(session, messageDto, messageService);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        TextMessage initialGreeting = new TextMessage("Welcome to bxxloob's velog!! :)");
        session.sendMessage(initialGreeting);
    }

}
