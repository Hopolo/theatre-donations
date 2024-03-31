/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.otus.theatredonations.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.otus.theatredonations.OAuth2UserService;
import ru.otus.theatredonations.model.donationalerts.DonationAlertsOAuth2User;
import ru.otus.theatredonations.services.DBServiceDonationAlertsUsers;

/**
 * @author Joe Grandja
 */
@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SecurityConfig {

    OAuth2UserService oAuth2UserService;
    DBServiceDonationAlertsUsers dbServiceDonationAlertsUsers;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest()
                .authenticated()
            ).oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo ->
                                      userInfo.userService(oAuth2UserService)).successHandler((request, response, authentication) -> {
                    DonationAlertsOAuth2User donationAlertsOAuth2User = (DonationAlertsOAuth2User) authentication.getPrincipal();
                    dbServiceDonationAlertsUsers.saveUser(donationAlertsOAuth2User);
                }));
        return http.build();
    }
}