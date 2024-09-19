package com.neoflex.vacationpackages.controller;

import com.neoflex.vacationpackages.service.CalculateService;
import com.neoflex.vacationpackages.dto.VacationPackageDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@RestController
@RequestMapping("/calculate")
@Validated
public class CalculateController {

    private final CalculateService calculateService;

    public CalculateController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @RequestMapping(method = RequestMethod.GET, params = { "averageSalary", "numDays" })
    public ResponseEntity<VacationPackageDto> calculate(
            @Valid @Min(1)
            float averageSalary,
            @Valid @Min(1)
            Integer numDays) {

        float vacationPackages = calculateService.calculateVacationPackage(averageSalary, numDays);
        return ResponseEntity.ok(new VacationPackageDto(vacationPackages));
    }

    @RequestMapping(method = RequestMethod.GET, params = { "averageSalary", "beginDate", "endDate" })
    public ResponseEntity<?> calculate(
            @Valid @RequestParam(value = "averageSalary") @Min(1)
            float averageSalary,
            @Valid @RequestParam(value = "beginDate") @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull
            LocalDate beginDate,
            @Valid @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull
            LocalDate endDate) {

        if (!beginDate.isBefore(endDate)) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body("Validation error: End date must be after the start date.");
        }

        float vacationPackages = calculateService.calculateVacationPackages(
                averageSalary, beginDate, endDate);
        return ResponseEntity.ok(new VacationPackageDto(vacationPackages));
    }
}
