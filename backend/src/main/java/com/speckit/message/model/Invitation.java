package com.speckit.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {
    private String token;
    private String contact;
    private List<String> targetParticipants;
    private OffsetDateTime expiresAt;
}
