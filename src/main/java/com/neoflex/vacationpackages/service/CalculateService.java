package com.neoflex.vacationpackages.service;

import com.neoflex.vacationpackages.repository.DbHolidaysRepository;
import com.neoflex.vacationpackages.repository.HolidaysRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class CalculateService {

    private final HolidaysRepository repository;

    public CalculateService(DbHolidaysRepository repository) {
        this.repository = repository;
    }

    public float calculateVacationPackage(float averageSalary, Integer numDays) {
        float averageDayInMonth = 29.3f;
        return BigDecimal.valueOf((numDays / averageDayInMonth) * averageSalary)
                .setScale(2, RoundingMode.HALF_UP).floatValue();
    }

    public float calculateVacationPackages(float averageSalary, LocalDate beginDate, LocalDate endDate) {
        int numDays = calculateNumDays(beginDate, endDate);
        return calculateVacationPackage(averageSalary, numDays);
    }

    private int calculateNumDays(LocalDate beginDate, LocalDate endDate) {
        int workingDays = 0;
        List<String> holidays = repository.getHolidays();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        while(beginDate.isBefore(endDate) || beginDate.isEqual(endDate)) {
            if (!holidays.contains(beginDate.format(formatter))) {
                workingDays++;
            }
            beginDate = beginDate.plusDays(1);
        }

        return workingDays;
    }
}
