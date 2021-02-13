package de.nilssiegfried.mousemover.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nilssiegfried.mousemover.adapter.motor.PiMotorService;
import de.nilssiegfried.mousemover.domain.entity.Movement;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovementControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PiMotorService piMotorService;

    @Test
    void status() throws JsonProcessingException {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        String expected = objectMapper.writeValueAsString(new Movement(false, 10, 180));
        // When
        String res = restTemplate.getForObject("http://localhost:" + port + "/movement", String.class);
        // Then
        assertThat(res).isEqualTo(expected);
    }

    @Test
    void update() throws JsonProcessingException {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        Movement movement = new Movement(true, 11, 181);
        String expected = objectMapper.writeValueAsString(movement);

        // When
        restTemplate.postForObject("http://localhost:" + port + "/movement", movement, String.class);
        String res = restTemplate.getForObject("http://localhost:" + port + "/movement", String.class);

        // Then
        assertThat(res).isEqualTo(expected);
    }

    @Test
    void update2() throws JsonProcessingException {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        Movement movement = new Movement(true, 11, 181);
        String expected = objectMapper.writeValueAsString(movement);

        // When
        restTemplate.postForObject("http://localhost:" + port + "/movement", movement, String.class);
        String res = restTemplate.getForObject("http://localhost:" + port + "/movement", String.class);

        // Then
        assertThat(res).isEqualTo(expected);

     //   await().atLeast(Duration.ofSeconds(10))
       //         .atMost(Duration.ofSeconds(10))
         //       .until(() -> false);
    }
}