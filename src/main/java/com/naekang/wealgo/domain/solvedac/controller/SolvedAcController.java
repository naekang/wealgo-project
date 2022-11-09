package com.naekang.wealgo.domain.solvedac.controller;

import com.naekang.wealgo.domain.solvedac.service.SolvedAcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problems")
@RequiredArgsConstructor
public class SolvedAcController {

    private final SolvedAcService solvedAcService;

    @PostMapping("/level")
    public ResponseEntity<Integer> saveProblemsByLevel() {
        return ResponseEntity.ok(solvedAcService.saveProblemsByLevel());
    }

    @PostMapping("/class")
    public void saveProblemsByClass() {
        solvedAcService.saveProblemsByClass();
    }

    @PostMapping("/id")
    public void saveProblemsById() {
        solvedAcService.saveProblemsById();
    }

}
