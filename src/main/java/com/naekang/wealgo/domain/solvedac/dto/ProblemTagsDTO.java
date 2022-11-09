package com.naekang.wealgo.domain.solvedac.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemTagsDTO {

    private String key;

    private List<ProblemTagsDisplayNameDTO> displayNames;

}
