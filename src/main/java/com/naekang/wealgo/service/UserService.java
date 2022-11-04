package com.naekang.wealgo.service;

import com.naekang.wealgo.domain.User;
import com.naekang.wealgo.domain.UserProblemStats;
import com.naekang.wealgo.dto.SignUpRequestDTO;
import com.naekang.wealgo.repository.UserProblemStatsRepository;
import com.naekang.wealgo.repository.UserRepository;
import com.naekang.wealgo.type.ProblemLevel;
import com.naekang.wealgo.type.UserRole;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserProblemStatsRepository userProblemStatsRepository;

    @Transactional
    public String signUp(SignUpRequestDTO signUpRequestDTO) throws Exception {
        if (userRepository.findByEmail(signUpRequestDTO.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (!signUpRequestDTO.getPassword().equals(signUpRequestDTO.getCheckedPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        User newUser = User.builder()
            .email(signUpRequestDTO.getEmail())
            .username(signUpRequestDTO.getUsername())
            .password(passwordEncoder.encode(signUpRequestDTO.getPassword()))
            .role(UserRole.USER)
            .build();

        userRepository.save(newUser);

        return newUser.getEmail();
    }

    @Scheduled(cron = "0 0 23 * * *")
    @Transactional
    public void saveUserProblemStats() throws ParseException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
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
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
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
