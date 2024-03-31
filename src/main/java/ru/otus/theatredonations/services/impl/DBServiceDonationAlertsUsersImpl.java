package ru.otus.theatredonations.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.otus.theatredonations.model.donationalerts.DonationAlertsOAuth2User;
import ru.otus.theatredonations.repository.DonationAlertsUserRepository;
import ru.otus.theatredonations.services.DBServiceDonationAlertsUsers;
import ru.otus.theatredonations.sessionmanager.TransactionManager;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DBServiceDonationAlertsUsersImpl implements DBServiceDonationAlertsUsers {

    DonationAlertsUserRepository userRepository;
    TransactionManager transactionManager;

    @Override
    public DonationAlertsOAuth2User saveUser(DonationAlertsOAuth2User donationAlertsOAuth2User) {
        return transactionManager.doInTransaction(() -> {
            var userCloned = donationAlertsOAuth2User.clone();
            var savedUser = userRepository.save(userCloned);
            log.info("saved DonationAlerts user: {}", savedUser);
            return savedUser;
        });
    }
}
