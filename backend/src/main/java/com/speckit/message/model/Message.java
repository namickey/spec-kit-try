package com.speckit.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String id;
    @NotBlank
    private String senderId;
    private List<String> recipientIds;
    private String body;
    private String attachmentRef;
    private OffsetDateTime createdAt;
}
