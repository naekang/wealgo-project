package com.naekang.wealgo.domain.solvedac.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemsByIdDetailsDTO {

    private int problemId;

    private String titleKo;

    private int level;

    private List<ProblemTagsDTO> tags;

}
