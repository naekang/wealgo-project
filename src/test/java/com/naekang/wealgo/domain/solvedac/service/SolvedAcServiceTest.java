package com.naekang.wealgo.domain.solvedac.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import com.naekang.wealgo.domain.solvedac.repository.ProblemsByLevelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class SolvedAcServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ProblemsByLevelRepository problemsByLevelRepository;

    @InjectMocks
    private SolvedAcService solvedAcService;

    @Test
    void saveProblemsByLevel_Success() {
        //given
        lenient().when(restTemplate.exchange(
                "https://solved.ac/api/v3/problem/level",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Object>() {
                }))
            .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        //when
        int count = solvedAcService.saveProblemsByLevel();

        //then
        assertEquals(31, count);
    }

}