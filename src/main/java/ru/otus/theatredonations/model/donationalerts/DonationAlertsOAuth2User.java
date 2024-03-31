package ru.otus.theatredonations.model.donationalerts;

import java.util.Collection;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Table("donation_alerts_users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonationAlertsOAuth2User implements OAuth2User, Cloneable {

    @Transient
    OAuth2User oAuth2User;
    @Transient
    Map<String, Object> attributes;
    @Id
    Long id;
    String name;
    Integer donationAlertsId;
    String code;
    Integer isActive;
    String avatar;
    String email;
    String language;
    String socketConnectionToken;

    public DonationAlertsOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
        this.attributes = oAuth2User.getAttribute("data");
        this.donationAlertsId = (Integer) attributes.get("id");
        this.code = (String) attributes.get("code");
        this.name = (String) attributes.get("name");
        this.isActive = (Integer) attributes.get("is_active");
        this.avatar = (String) attributes.get("avatar");
        this.email = (String) attributes.get("email");
        this.language = (String) attributes.get("language");
        this.socketConnectionToken = (String) attributes.get("socket_connection_token");
    }

    @PersistenceCreator
    public DonationAlertsOAuth2User(
        Long id,
        String name,
        Integer donationAlertsId,
        String code,
        Integer isActive,
        String avatar,
        String email,
        String language,
        String socketConnectionToken
    ) {
        this.id = id;
        this.name = name;
        this.donationAlertsId = donationAlertsId;
        this.code = code;
        this.isActive = isActive;
        this.avatar = avatar;
        this.email = email;
        this.language = language;
        this.socketConnectionToken = socketConnectionToken;
    }

    @Override
    @Transient
    public <A> A getAttribute(String name) {
        return oAuth2User.getAttribute(name);
    }

    @Override
    @Transient
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public DonationAlertsOAuth2User clone() {
        return new DonationAlertsOAuth2User(
            this.id,
            this.name,
            this.donationAlertsId,
            this.code,
            this.isActive,
            this.avatar,
            this.email,
            this.language,
            this.socketConnectionToken
        );
    }

}