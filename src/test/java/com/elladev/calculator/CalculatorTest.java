package com.elladev.calculator;

import com.elladev.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    private CalculatorService calculator = new CalculatorService();

    @Test
    void testSum() {
        assertEquals(5, calculator.sum(2, 3));
    }
}