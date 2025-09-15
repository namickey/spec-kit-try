package com.speckit.message.controller;

import com.speckit.message.model.Message;
import jakarta.validation.Valid;
import com.speckit.message.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> create(@Valid @RequestBody Message m) {
        Message created = messageService.create(m);
        return ResponseEntity.created(URI.create("/messages/" + created.getId())).body(created);
    }

    @org.springframework.web.bind.annotation.GetMapping
    public ResponseEntity<java.util.List<Message>> listForRecipient(@org.springframework.web.bind.annotation.RequestParam("recipientId") String recipientId) {
        java.util.List<Message> list = messageService.findByRecipientId(recipientId);
        return ResponseEntity.ok(list);
    }
}
