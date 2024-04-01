package com.vacationpackages.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
@Repository
public class HolidaysRepository {
    private final Set<String> storage = new HashSet<>() {{
        add("01-01");
        add("01-07");
        add("02-23");
        add("03-08");
        add("05-01");
        add("05-09");
        add("06-12");
        add("11-04");
    }};

    public boolean isHoliday(LocalDate date) {
        return storage.contains(date.format(DateTimeFormatter.ofPattern("MM-dd")));
    }
}
