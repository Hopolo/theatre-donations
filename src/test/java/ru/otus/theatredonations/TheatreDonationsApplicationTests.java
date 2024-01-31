package ru.otus.theatredonations;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TheatreDonationsApplicationTests {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TheatreDonationsApplicationTests.class);

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Test
    void restTemplateRequest() {
        System.out.println(restTemplateBuilder
                               .build()
                               .getForEntity(URI.create("https://www.donationalerts.com/api/v1/alerts/donations"), String.class));
    }
}
