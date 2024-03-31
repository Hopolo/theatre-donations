package ru.otus.theatredonations.model.donationalerts;

import lombok.Data;

@Data
public class MetaInfLink {
    private String url;
    private String label;
    private Boolean active;
}
