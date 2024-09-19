package com.neoflex.vacationpackages.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CalculateServiceTest {
    private final CalculateService calculateService;

    @Test
    void calculateWithTest() {
        Assertions.assertEquals(calculateService.calculateVacationPackage(29300, 28),
                28000,
                "Wrong calculation");
    }

    @Test
    void calculateWithDatesTest() {
        float averageSalary = 29300.0f;
        LocalDate beginDate = LocalDate.of(2024, 1, 6);
        LocalDate endDate = LocalDate.of(2024, 2, 4);

        double averageDaysInMonth = 29.3;
        Assertions.assertEquals(
                calculateService.calculateVacationPackages(averageSalary, beginDate, endDate),
                28000,
                "Wrong calculation");
    }



}
