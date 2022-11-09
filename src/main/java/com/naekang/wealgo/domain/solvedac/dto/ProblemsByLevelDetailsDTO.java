package com.naekang.wealgo.domain.solvedac.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemsByLevelDetailsDTO {

    private int level;

    private int count;

}
