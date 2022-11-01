package com.naekang.wealgo.type;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProblemLevelTest {

    @Test
    @DisplayName("문제 난이도 번호 -> 문자열 성공 케이스")
    void idxToLevel_Success() {
        // given & when
        String test0 = ProblemLevel.idxToLevel(0);
        String test12 = ProblemLevel.idxToLevel(12);
        String test30 = ProblemLevel.idxToLevel(30);

        //then
        assertEquals("Unrated", test0);
        assertEquals("Gold IV", test12);
        assertEquals("Ruby I", test30);
    }

    @Test
    @DisplayName("문제 난이도 번호가 잘못된 경우")
    void idxToLevel_ExceptionThrown_InvalidIndex() {
        // given & when
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> ProblemLevel.idxToLevel(31));

        //then
        assertEquals("입력 값의 범위가 잘못되었습니다.", exception.getMessage());
    }

}