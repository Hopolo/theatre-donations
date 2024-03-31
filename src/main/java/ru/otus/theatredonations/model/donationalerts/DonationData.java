package ru.otus.theatredonations.model.donationalerts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DonationData {
    private Integer id;
    private String name;
    private String username;
    private String message;
    @JsonProperty("message_type")
    private String messageType;
    @JsonProperty("payin_system")
    private PaymentSystem paymentSystem;
    private Integer amount;
    private String currency;
    @JsonProperty("is_shown")
    private Integer isShown;
    @JsonProperty("amount_in_user_currency")
    private Integer amountInUserCurrency;
    @JsonProperty("recipient_name")
    private String recipientName;
    private Recipient recipient;
    @JsonProperty("created_at")
    private String createdAtDateTime;
    @JsonProperty("created_at_ts")
    private Long createdAtTs;
    @JsonProperty("shown_at")
    private String shownAt;
    @JsonProperty("shown_at_ts")
    private Long shownAtTs;
}
