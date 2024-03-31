package ru.otus.theatredonations.model.donationalerts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Recipient {
    @JsonProperty("user_id")
    private Integer userId;
    private String code;
    private String name;
    private String avatar;
}
