package com.speckit.message.service;

import com.speckit.message.mapper.MessageMapper;
import com.speckit.message.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageServiceTest {
    private MessageMapper mapper;
    private MessageService service;

    @BeforeEach
    void setUp() {
        mapper = mock(MessageMapper.class);
        service = new MessageService(mapper);
    }

    @Test
    void create_withNoRecipients_shouldNotCallInsertRecipient() {
        Message m = new Message();
        m.setBody("hi");
        m.setSenderId("s1");

        Message created = service.create(m);

        verify(mapper, times(1)).insert(any(Message.class));
        verify(mapper, never()).insertRecipient(anyString(), anyString());
        assertThat(created.getId()).isNotNull();
    }

    @Test
    void create_withRecipients_shouldInsertRecipients() {
        Message m = new Message();
        m.setBody("hi");
        m.setSenderId("s1");
        m.setRecipientIds(List.of("r1","r2"));

        Message created = service.create(m);

        verify(mapper, times(1)).insert(any(Message.class));
        verify(mapper, times(2)).insertRecipient(eq(created.getId()), anyString());
    }
}
