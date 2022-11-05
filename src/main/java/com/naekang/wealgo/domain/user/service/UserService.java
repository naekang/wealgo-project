package com.naekang.wealgo.domain.user.service;

import com.naekang.wealgo.annotation.Timer;
import com.naekang.wealgo.domain.user.entity.UserProblemStats;
import com.naekang.wealgo.domain.user.repository.UserProblemStatsRepository;
import com.naekang.wealgo.domain.user.type.ProblemLevel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProblemStatsRepository userProblemStatsRepository;

    @Scheduled(cron = "0 0 23 * * *")
    @Transactional
    @Timer
    public void saveUserProblemStats() throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(getString());

        for (int i = 0; i < 31; i++) {

            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            UserProblemStats userProblemStats = UserProblemStats.builder()
                .level(ProblemLevel.idxToLevel(i))
                .totalProblem(Integer.parseInt(jsonObject.get("total").toString()))
                .solvedProblem(Integer.parseInt(jsonObject.get("solved").toString()))
                .exp(Integer.parseInt(jsonObject.get("exp").toString()))
                .build();

            Optional<UserProblemStats> findObject = userProblemStatsRepository.findById(
                (long) i + 1);

            if (findObject.isPresent() && findObject.get().equals(userProblemStats)) {
                continue;
            }

            userProblemStatsRepository.save(userProblemStats);
        }
    }

    private String getString() {
        String apiUrl = "https://solved.ac/api/v3/user/problem_stats?handle=naekang";

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            BufferedReader br;

            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return response.toString();
        } catch (Exception e) {
            return "Failed to get response";
        }
    }

}
