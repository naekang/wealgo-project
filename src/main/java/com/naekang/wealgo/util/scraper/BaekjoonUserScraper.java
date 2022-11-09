package com.naekang.wealgo.util.scraper;

import com.naekang.wealgo.domain.user.dto.UserScraperDTO;
import com.naekang.wealgo.exception.CustomException;
import com.naekang.wealgo.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class BaekjoonUserScraper {

    private final static String bojUrl = "https://www.acmicpc.net/user/%s";

    public UserScraperDTO getSolvedProblem(String username) {
        List<Integer> solvedProblemList = new ArrayList<>();

        String url = String.format(bojUrl, username);

        Connection connection = Jsoup.connect(url);
        Document document = null;
        try {
            document = connection.get();
        } catch (Exception e) {
            throw new CustomException("존재하지 않는 Solved.ac 닉네임입니다.",
                ErrorCode.WRONG_SOLVED_AC_NICKNAME);
        }

        int solvedCnt = Integer.parseInt(
            Objects.requireNonNull(document.getElementById("u-solved")).text()
        );

        Elements elemList = document.getElementsByClass("problem-list");

        String solvedListText = elemList.get(0).text().replace(" ", ", ");

        return UserScraperDTO.builder()
            .solvedCount(solvedCnt)
            .solvedList(solvedListText)
            .build();
    }

}
