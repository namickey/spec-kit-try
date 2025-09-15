package com.speckit.message.controller;

import com.speckit.message.model.Message;
import com.speckit.message.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class MessageViewController {
    private final MessageService messageService;

    public MessageViewController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/ui/messages")
    public String list(@RequestParam(value = "recipientId", required = false) String recipientId, Model model) {
        if (recipientId == null) {
            recipientId = "";
        }
        model.addAttribute("recipientId", recipientId);
        model.addAttribute("messages", messageService.findByRecipientId(recipientId));
        model.addAttribute("newMessage", new Message(UUID.randomUUID().toString(), null, null, "", null, null));
        return "messages/list";
    }

    @PostMapping("/ui/messages")
    public String send(@ModelAttribute Message newMessage, @RequestParam(value = "recipientIdsRaw", required = false) String recipientIdsRaw) {
        if (recipientIdsRaw != null && !recipientIdsRaw.isBlank()) {
            String[] parts = recipientIdsRaw.split(",");
            newMessage.setRecipientIds(java.util.Arrays.asList(parts));
        }
        // leave senderId null for now
        messageService.create(newMessage);
        return "redirect:/ui/messages?recipientId=" + (newMessage.getRecipientIds() != null && !newMessage.getRecipientIds().isEmpty() ? newMessage.getRecipientIds().get(0) : "");
    }
}
