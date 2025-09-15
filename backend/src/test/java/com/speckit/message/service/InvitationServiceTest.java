package com.speckit.message.service;

import com.speckit.message.mapper.InvitationMapper;
import com.speckit.message.model.Invitation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class InvitationServiceTest {
    private InvitationMapper mapper;
    private InvitationService service;

    @BeforeEach
    void setUp() {
        mapper = mock(InvitationMapper.class);
        service = new InvitationService(mapper);
    }

    @Test
    void create_shouldGenerateTokenAndInsert() {
        Invitation inv = new Invitation();
        inv.setContact("a@b.com");

        Invitation created = service.create(inv);

        verify(mapper, times(1)).insert(any(Invitation.class));
        assertThat(created.getToken()).isNotNull();
    }

    @Test
    void findByToken_shouldDelegate() {
        when(mapper.findByToken("t1")).thenReturn(new Invitation());
        Invitation got = service.findByToken("t1");
        verify(mapper, times(1)).findByToken("t1");
        assertThat(got).isNotNull();
    }
}
