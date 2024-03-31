package ru.otus.theatredonations.model.donationalerts;

import java.util.List;
import lombok.Data;

@Data
public class ResponseDonationAlerts<T> {
    private List<T> data;
    private Links links;
    private MetaInf meta;
}
