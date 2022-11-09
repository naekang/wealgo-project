package com.naekang.wealgo.domain.solvedac.service;


import com.naekang.wealgo.annotation.Timer;
import com.naekang.wealgo.domain.solvedac.dto.ProblemTagsDTO;
import com.naekang.wealgo.domain.solvedac.dto.ProblemsByClassDetailsDTO;
import com.naekang.wealgo.domain.solvedac.dto.ProblemsByIdDetailsDTO;
import com.naekang.wealgo.domain.solvedac.dto.ProblemsByLevelDetailsDTO;
import com.naekang.wealgo.domain.solvedac.entity.ProblemsByClass;
import com.naekang.wealgo.domain.solvedac.entity.ProblemsById;
import com.naekang.wealgo.domain.solvedac.entity.ProblemsByLevel;
import com.naekang.wealgo.domain.solvedac.repository.ProblemsByClassRepository;
import com.naekang.wealgo.domain.solvedac.repository.ProblemsByIdRepository;
import com.naekang.wealgo.domain.solvedac.repository.ProblemsByLevelRepository;
import com.naekang.wealgo.domain.user.type.ProblemLevel;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolvedAcService {

    private static final String levelUrl = "https://solved.ac/api/v3/problem/level";
    private static final String classUrl = "https://solved.ac/api/v3/problem/class";
    private static final String idUrl = "https://solved.ac/api/v3/problem/lookup?problemIds=%d";
    private static final String bojUrl = "https://www.acmicpc.net/problem/%d";

    private final ProblemsByLevelRepository problemsByLevelRepository;
    private final ProblemsByClassRepository problemsByClassRepository;
    private final ProblemsByIdRepository problemsByIdRepository;
    private static final RestTemplate template = new RestTemplate();

    @Timer
    @Transactional
    public int saveProblemsByLevel() {
        ResponseEntity<List<ProblemsByLevelDetailsDTO>> response = template.exchange(levelUrl,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {
            });

        List<ProblemsByLevelDetailsDTO> list = response.getBody();

        for (int i = 0; i < Objects.requireNonNull(list).size(); i++) {
            ProblemsByLevelDetailsDTO problem = list.get(i);

            String level = ProblemLevel.idxToLevel(problem.getLevel());
            int problemsCount = problem.getCount();

            problemsByLevelRepository.save(ProblemsByLevel.builder()
                .level(level)
                .problemsCount(problemsCount)
                .build());
        }

        return list.size();
    }

    @Timer
    @Transactional
    public void saveProblemsByClass() {
        ResponseEntity<List<ProblemsByClassDetailsDTO>> response = template.exchange(classUrl,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {
            });

        List<ProblemsByClassDetailsDTO> list = response.getBody();

        for (int i = 0; i < Objects.requireNonNull(list).size(); i++) {
            ProblemsByClassDetailsDTO problem = list.get(i);

            int classLevel = problem.getClassLevel();
            int count = problem.getTotal();
            int essentials = problem.getEssentials();

            problemsByClassRepository.save(ProblemsByClass.builder()
                .classLevel(classLevel)
                .problemsCount(count)
                .essentialCount(essentials)
                .build());
        }
    }

    @Timer
    @Transactional
    public void saveProblemsById() {
        for (int idx = 1000; idx <= 1100; idx++) {
            String url = String.format(idUrl, idx);

            ResponseEntity<List<ProblemsByIdDetailsDTO>> response = template.exchange(url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

            List<ProblemsByIdDetailsDTO> list = response.getBody();

            ProblemsByIdDetailsDTO problem = list.get(0);

            if (!ObjectUtils.isEmpty(problem)) {
                int problemId = problem.getProblemId();
                String title = problem.getTitleKo();
                String level = ProblemLevel.idxToLevel(problem.getLevel());
                List<ProblemTagsDTO> tagList = problem.getTags();

                String representativeTag = "";
                if (tagList.size() != 0) {
                    representativeTag = tagList.get(0).getDisplayNames().get(0).getName();
                }

                String problemUrl = String.format(bojUrl, problemId);

                problemsByIdRepository.save(ProblemsById.builder()
                    .problemId(problemId)
                    .title(title)
                    .level(level)
                    .representativeTag(representativeTag)
                    .problemUrl(problemUrl)
                    .build());
            }
        }
    }
}
