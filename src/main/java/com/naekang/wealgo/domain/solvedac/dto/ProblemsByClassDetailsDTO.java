package com.naekang.wealgo.domain.solvedac.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemsByClassDetailsDTO {

    @JsonProperty("class")
    private int classLevel;

    private int total;

    private int essentials;

}
