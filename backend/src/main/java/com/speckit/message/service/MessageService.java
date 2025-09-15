package com.speckit.message.service;

import com.speckit.message.mapper.MessageMapper;
import com.speckit.message.model.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MessageService {
    private final MessageMapper messageMapper;

    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public Message create(Message m) {
        String id = UUID.randomUUID().toString();
        m.setId(id);
        m.setCreatedAt(OffsetDateTime.now());
        messageMapper.insert(m);
        if (m.getRecipientIds() != null) {
            for (String r : m.getRecipientIds()) {
                messageMapper.insertRecipient(id, r);
            }
        }
        return m;
    }

    public List<Message> findByRecipientId(String recipientId) {
        return messageMapper.findByRecipientId(recipientId);
    }
}
