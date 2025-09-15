package integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = com.speckit.message.Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InviteFlowIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void invite_unregistered_contact_shouldCreateInvitation() {
        // ensure contact not in users
        String contact = "newcomer@example.com";
        jdbcTemplate.update("DELETE FROM users WHERE contact = ?", contact);

        String url = "http://localhost:" + port + "/invitations";
        Map<String, Object> body = Map.of(
                "contact", contact,
                "target_participants", new String[]{}
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> postResp = restTemplate.postForEntity(url, request, String.class);
        assertThat(postResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResp.getBody()).contains("token");

        // Optionally verify in DB
        // String token = ... extract from response
    }
}
