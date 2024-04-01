package com.vacationpackages.service;

import com.vacationpackages.enums.CalculateTypeValues;
import com.vacationpackages.repository.HolidaysRepository;
import org.joda.time.Days;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CalculateService {

    private final HolidaysRepository repository;

    public CalculateService(HolidaysRepository repository) {
        this.repository = repository;
    }

    public Float calculateVacationPackage(Integer averageSalary, CalculateTypeValues calculateTypeValues,
                                          Integer numDays, LocalDate startDate, LocalDate endDate) {

        int numDaysInVacation = 0;

        switch (calculateTypeValues) {
            case NUM_DAYS:
                if (numDays == null) {
                    throw new IllegalArgumentException("invalid numDays");
                } else {
                    numDaysInVacation = numDays;
                }
                break;
            case DATES:
                if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
                    throw new IllegalArgumentException("invalid startDate or endDate");
                } else {
                    numDaysInVacation = calculateNumDays(startDate, endDate);
                }
                break;
            default:
                break;
        }

        return (numDaysInVacation / 29.3f) * averageSalary;
    }

    private int calculateNumDays(LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;
        while(startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            if (!DayOfWeek.SATURDAY.equals(startDate.getDayOfWeek())
                    && !DayOfWeek.SUNDAY.equals(startDate.getDayOfWeek())
                    && !repository.isHoliday(startDate)) {
                workingDays++;
            }
            startDate = startDate.plusDays(1);
        }

        return workingDays;
    }


}
