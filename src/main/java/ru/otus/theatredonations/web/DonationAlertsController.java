package ru.otus.theatredonations.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.theatredonations.integration.DonationAlertsClient;
import ru.otus.theatredonations.model.donationalerts.DonationData;

@RestController
@RequestMapping("/donation-alerts")
@Log4j2
@RequiredArgsConstructor
public class DonationAlertsController {

    private final DonationAlertsClient donationAlertsClient;
    private final DonationsSubscribe donationsSubscribe;

    @GetMapping("/donations")
    List<DonationData> useOauthWithAuthCode() {
        log.info("get request for donations list");
        return donationAlertsClient.getDonations();
    }

    @GetMapping("/subscribe")
    void subscribe(Authentication authentication) {
        log.info("get request for subscribing on donations");
        donationsSubscribe.subscribe((OAuth2User) authentication.getPrincipal());
    }
}
