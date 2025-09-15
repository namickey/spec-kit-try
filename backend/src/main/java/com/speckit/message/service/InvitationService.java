package com.speckit.message.service;

import com.speckit.message.mapper.InvitationMapper;
import com.speckit.message.model.Invitation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional
public class InvitationService {
    private final InvitationMapper invitationMapper;

    public InvitationService(InvitationMapper invitationMapper) {
        this.invitationMapper = invitationMapper;
    }

    public Invitation create(Invitation inv) {
        String token = UUID.randomUUID().toString();
        inv.setToken(token);
        inv.setExpiresAt(OffsetDateTime.now().plus(7, ChronoUnit.DAYS));
        invitationMapper.insert(inv);
        return inv;
    }

    public Invitation findByToken(String token) {
        return invitationMapper.findByToken(token);
    }
}
