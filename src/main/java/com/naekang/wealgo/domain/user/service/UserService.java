package com.naekang.wealgo.domain.user.service;

import com.naekang.wealgo.annotation.Timer;
import com.naekang.wealgo.domain.user.dto.UserProblemStatsDTO;
import com.naekang.wealgo.domain.user.entity.UserProblemStats;
import com.naekang.wealgo.domain.user.repository.UserProblemStatsRepository;
import com.naekang.wealgo.domain.user.type.ProblemLevel;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private static final String apiUrl = "https://solved.ac/api/v3/user/problem_stats?handle=naekang";

    private final UserProblemStatsRepository userProblemStatsRepository;

    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    @Timer
    public void saveUserProblemStats() {
        RestTemplate template = new RestTemplate();

        ResponseEntity<List<UserProblemStatsDTO>> response = template.exchange(apiUrl,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {
            });

        List<UserProblemStatsDTO> list = response.getBody();

        for (int i = 0; i < Objects.requireNonNull(list).size(); i++) {
            UserProblemStatsDTO problemStatsDTO = list.get(i);

            UserProblemStats userProblemStats = UserProblemStats.builder()
                .level(ProblemLevel.idxToLevel(problemStatsDTO.getLevel()))
                .solvedProblem(problemStatsDTO.getSolved())
                .exp(problemStatsDTO.getExp())
                .build();

            Optional<UserProblemStats> findObject = userProblemStatsRepository.findById(
                (long) i + 1);

            if (findObject.isPresent() && findObject.get().equals(userProblemStats)) {
                continue;
            }

            userProblemStatsRepository.save(userProblemStats);
        }
    }

}
