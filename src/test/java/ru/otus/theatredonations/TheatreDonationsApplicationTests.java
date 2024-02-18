package ru.otus.theatredonations;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
@ActiveProfiles("test")
class TheatreDonationsApplicationTests {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TheatreDonationsApplicationTests.class);

    @Autowired
    private WebClient webClient;

    @Test
    void webClientRequest() {
        System.out.println(webClient
                               .get()
                               .uri(URI.create("https://www.donationalerts.com/api/v1/alerts/donations"))
                               .retrieve()
                               .bodyToMono(String.class)
                               .block());
    }

}
