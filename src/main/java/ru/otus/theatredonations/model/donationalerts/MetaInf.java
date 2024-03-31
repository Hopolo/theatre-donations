package ru.otus.theatredonations.model.donationalerts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;
import lombok.Data;

@Data
@JsonRootName("meta")
public class MetaInf {
    @JsonProperty("current_page")
    private Integer currentPage;
    private Integer from;
    @JsonProperty("last_page")
    private Integer lastPage;
    private List<MetaInfLink> links;
    private String path;
    @JsonProperty("per_page")
    private Integer perPage;
    private Integer to;
    private Integer total;
}
