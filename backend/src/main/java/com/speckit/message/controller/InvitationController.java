package com.speckit.message.controller;

import com.speckit.message.model.Invitation;
import com.speckit.message.service.InvitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/invitations")
public class InvitationController {
    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping
    public ResponseEntity<Invitation> create(@RequestBody Invitation inv) {
        Invitation created = invitationService.create(inv);
        return ResponseEntity.created(URI.create("/invitations/" + created.getToken())).body(created);
    }
}
