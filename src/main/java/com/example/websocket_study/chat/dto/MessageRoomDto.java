package com.example.websocket_study.chat.dto;

import com.example.websocket_study.chat.service.MessageService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MessageRoomDto {
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public MessageRoomDto(String roomId) {
        this.roomId = roomId;
    }

    public void handleActions(WebSocketSession session, MessageDto messageDto, MessageService messageService) {
        if (messageDto.getMessageType().equals(MessageDto.MessageType.ENTER)) {
            sessions.add(session);
            messageDto.setMessage(messageDto.getSender() + "님이 입장했습니다.");
        }
        sendMessage(messageDto, messageService);
    }

    public <T> void sendMessage(T message, MessageService messageService) {
        sessions.parallelStream().forEach(session -> messageService.sendMessage(session, message));

    }
}
