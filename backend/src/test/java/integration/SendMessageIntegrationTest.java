package integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.*;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = com.speckit.message.Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SendMessageIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void sendMessage_flow_shouldDeliverToRecipient() {
        // create sender and recipient
        String senderId = UUID.randomUUID().toString();
        String recipientId = UUID.randomUUID().toString();
        jdbcTemplate.update("INSERT INTO users(id, name, contact) VALUES (?, ?, ?)", senderId, "sender", "s@example.com");
        jdbcTemplate.update("INSERT INTO users(id, name, contact) VALUES (?, ?, ?)", recipientId, "recipient", "r@example.com");

        String url = "http://localhost:" + port + "/messages";
        Map<String, Object> body = Map.of(
                "senderId", senderId,
                "recipientIds", new String[]{recipientId},
                "body", "Hello recipient"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> postResp = restTemplate.postForEntity(url, request, String.class);
        assertThat(postResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // recipient GETs messages (endpoint not implemented yet)
        String getUrl = "http://localhost:" + port + "/messages?recipientId=" + recipientId;
        ResponseEntity<String> getResp = restTemplate.getForEntity(getUrl, String.class);

        // Expect 200 and body contains the message
        assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResp.getBody()).contains("Hello recipient");
    }
}
