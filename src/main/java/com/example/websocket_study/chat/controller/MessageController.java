package com.example.websocket_study.chat.controller;

import com.example.websocket_study.chat.dto.MessageRoomDto;
import com.example.websocket_study.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class MessageController {
    
    private final MessageService messageService;

    @PostMapping
    public MessageRoomDto createRoom(@RequestParam String name) {
        return messageService.createRoom(name);
    }

    @GetMapping
    public List<MessageRoomDto> findAllRoom() {
        return messageService.findAllRoom();
    }
}
