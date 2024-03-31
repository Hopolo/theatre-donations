package ru.otus.theatredonations.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.theatredonations.model.donationalerts.DonationAlertsOAuth2User;

public interface DonationAlertsUserRepository extends ListCrudRepository<DonationAlertsOAuth2User, UUID> {

    Optional<DonationAlertsOAuth2User> findByName(String name);

}
