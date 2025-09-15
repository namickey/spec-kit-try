package contract;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.UUID;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = com.speckit.message.Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractMessagesPostTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void postMessages_shouldReturn201AndId() {
        String url = "http://localhost:" + port + "/messages";
    Map<String, Object> body = Map.of(
        "senderId", UUID.randomUUID().toString(),
        "recipientIds", new String[]{UUID.randomUUID().toString()},
        "body", "Hello"
    );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        // Expect 201 and response body contains id
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).contains("id");
    }
}
