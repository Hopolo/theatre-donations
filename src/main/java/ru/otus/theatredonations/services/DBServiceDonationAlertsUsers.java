package ru.otus.theatredonations.services;

import ru.otus.theatredonations.model.donationalerts.DonationAlertsOAuth2User;

public interface DBServiceDonationAlertsUsers {
    DonationAlertsOAuth2User saveUser(DonationAlertsOAuth2User donationAlertsOAuth2User);
}
