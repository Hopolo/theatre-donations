package ru.otus.theatredonations.web;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.github.centrifugal.centrifuge.Client;
import io.github.centrifugal.centrifuge.ConnectedEvent;
import io.github.centrifugal.centrifuge.ConnectingEvent;
import io.github.centrifugal.centrifuge.DisconnectedEvent;
import io.github.centrifugal.centrifuge.DuplicateSubscriptionException;
import io.github.centrifugal.centrifuge.ErrorEvent;
import io.github.centrifugal.centrifuge.EventListener;
import io.github.centrifugal.centrifuge.JoinEvent;
import io.github.centrifugal.centrifuge.LeaveEvent;
import io.github.centrifugal.centrifuge.MessageEvent;
import io.github.centrifugal.centrifuge.Options;
import io.github.centrifugal.centrifuge.PublicationEvent;
import io.github.centrifugal.centrifuge.ServerJoinEvent;
import io.github.centrifugal.centrifuge.ServerLeaveEvent;
import io.github.centrifugal.centrifuge.ServerPublicationEvent;
import io.github.centrifugal.centrifuge.ServerSubscribedEvent;
import io.github.centrifugal.centrifuge.ServerSubscribingEvent;
import io.github.centrifugal.centrifuge.ServerUnsubscribedEvent;
import io.github.centrifugal.centrifuge.SubscribedEvent;
import io.github.centrifugal.centrifuge.SubscribingEvent;
import io.github.centrifugal.centrifuge.Subscription;
import io.github.centrifugal.centrifuge.SubscriptionErrorEvent;
import io.github.centrifugal.centrifuge.SubscriptionEventListener;
import io.github.centrifugal.centrifuge.SubscriptionOptions;
import io.github.centrifugal.centrifuge.UnsubscribedEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class DonationsSubscribe {

    public static final String WEBSOCKET_URL = "wss://centrifugo.donationalerts.com/connection/websocket";
    private final OAuth2AuthorizedClientService clientService;
    private boolean stop = false;

    public void subscribe(OAuth2User user) {

        EventListener listener = new EventListener() {
            @Override
            public void onConnected(
                Client client,
                ConnectedEvent event
            ) {
                log.debug("connected with client id {}}", event.getClient());

                SubscriptionEventListener subListener = new SubscriptionEventListener() {
                    @Override
                    public void onSubscribed(
                        Subscription sub,
                        SubscribedEvent event
                    ) {
                        log.debug("subscribed to " + sub.getChannel() + ", recovered " + event.getRecovered());
                        String data = "{\"input\": \"I just subscribed to channel\"}";
                        sub.publish(data.getBytes(), (err, res) -> {
                            if (err != null) {
                                log.debug("error publish: " + err.getMessage());
                                return;
                            }
                            log.debug("successfully published");
                        });
                    }

                    @Override
                    public void onSubscribing(
                        Subscription sub,
                        SubscribingEvent event
                    ) {
                        log.debug("subscribing: {}}", event.getReason());
                    }

                    @Override
                    public void onUnsubscribed(
                        Subscription sub,
                        UnsubscribedEvent event
                    ) {
                        log.debug("unsubscribed " + sub.getChannel() + ", reason: " + event.getReason());
                    }

                    @Override
                    public void onError(
                        Subscription sub,
                        SubscriptionErrorEvent event
                    ) {
                        log.debug("subscription error " + sub.getChannel() + " " + event.getError().toString());
                    }

                    @Override
                    public void onPublication(
                        Subscription sub,
                        PublicationEvent event
                    ) {
                        String data = new String(event.getData(), UTF_8);
                        log.debug("message from " + sub.getChannel() + " " + data);
                    }

                    @Override
                    public void onJoin(
                        Subscription sub,
                        JoinEvent event
                    ) {
                        log.debug("client " + event.getInfo().getClient() + " joined channel " + sub.getChannel());
                    }

                    @Override
                    public void onLeave(
                        Subscription sub,
                        LeaveEvent event
                    ) {
                        log.debug("client " + event.getInfo().getClient() + " left channel " + sub.getChannel());
                    }
                };

                Subscription sub;
                try {
                    sub = client.newSubscription("$alerts:donation_11097143", new SubscriptionOptions(), subListener);
                } catch (DuplicateSubscriptionException e) {
                    e.printStackTrace();
                    return;
                }
                sub.subscribe();
            }

            @Override
            public void onConnecting(
                Client client,
                ConnectingEvent event
            ) {
                log.debug("connecting: {}}", event.getReason());
                log.debug("connecting: {}", client);

            }

            @Override
            public void onDisconnected(
                Client client,
                DisconnectedEvent event
            ) {
                log.debug("disconnected {}} {}", event.getCode(), event.getReason());
                stop = true;
            }

            @Override
            public void onError(
                Client client,
                ErrorEvent event
            ) {
                log.debug("connection error: {}", Arrays.toString(event.getError().getStackTrace()));
                log.debug("connection error: {}", event.getError().getMessage());
            }

            @Override
            public void onMessage(
                Client client,
                MessageEvent event
            ) {
                String data = new String(event.getData(), UTF_8);
                log.debug("message received: " + data);
            }

            @Override
            public void onSubscribed(
                Client client,
                ServerSubscribedEvent event
            ) {
                log.debug("server side subscribed: " + event.getChannel() + ", recovered " + event.getRecovered());
            }

            @Override
            public void onSubscribing(
                Client client,
                ServerSubscribingEvent event
            ) {
                log.debug("server side subscribing: " + event.getChannel());
            }

            @Override
            public void onUnsubscribed(
                Client client,
                ServerUnsubscribedEvent event
            ) {
                log.debug("server side unsubscribed: " + event.getChannel());
            }

            @Override
            public void onPublication(
                Client client,
                ServerPublicationEvent event
            ) {
                String data = new String(event.getData(), UTF_8);
                log.debug("server side publication: " + event.getChannel() + ": " + data);
            }

            @Override
            public void onJoin(
                Client client,
                ServerJoinEvent event
            ) {
                log.debug("server side join: " + event.getChannel() + " from client " + event.getInfo().getClient());
            }

            @Override
            public void onLeave(
                Client client,
                ServerLeaveEvent event
            ) {
                log.debug("server side leave: " + event.getChannel() + " from client " + event.getInfo().getClient());
            }
        };
        Options options = new Options();
        options.setToken((String) ((Map) user.getAttribute("data")).get("socket_connection_token"));
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + getAccessToken());
        options.setHeaders(headers);
        Client client = new Client(WEBSOCKET_URL, options, listener);
        client.connect();

        while (!stop) {
        }
    }

    private String getAccessToken() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken)
            securityContext.getAuthentication();

        OAuth2AuthorizedClient authorizedClient = clientService.loadAuthorizedClient(
            oauth2Token.getAuthorizedClientRegistrationId(),
            oauth2Token.getName()
        );

        return authorizedClient.getAccessToken().getTokenValue();
    }
}
