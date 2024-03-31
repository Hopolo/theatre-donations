package ru.otus.theatredonations.integration;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.otus.theatredonations.model.donationalerts.DonationData;
import ru.otus.theatredonations.model.donationalerts.ResponseDonationAlerts;

@Component
@RequiredArgsConstructor
public class DonationAlertsClient {

    private final WebClient webClient;

    public List<DonationData> getDonations() {
        Mono<ResponseDonationAlerts<DonationData>> responseMono = webClient.get()
            .uri(URI.create("https://www.donationalerts.com/api/v1/alerts/donations"))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ResponseDonationAlerts<DonationData>>() {
            })
            .log();
        Optional<ResponseDonationAlerts<DonationData>> responseDonationAlerts = Optional.ofNullable(responseMono.block());
        return responseDonationAlerts.orElseThrow().getData();
    }
}
