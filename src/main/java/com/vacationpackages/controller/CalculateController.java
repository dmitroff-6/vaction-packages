package com.vacationpackages.controller;

import com.vacationpackages.enums.CalculateTypeValues;
import com.vacationpackages.service.CalculateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.LocalDate;


@RestController
@RequestMapping("/calculate")
@Api(value = "calculate", description = "Вычисляет отпускные по количиству дней или крайним датам")
@Validated
public class CalculateController {

    private final CalculateService calculateService;

    public CalculateController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @ApiOperation(value = "averageSalary - средняя зарплата за год\n" +
            "calculateTypeValues - выбор данных по которым будут считаться отпускные\n" +
            "numDays - количество дней отпуска\n" +
            "startDate - дата начал отпуска (format yyyy-MM-dd)\n" +
            "endDate -дата окончания отпуска(format yyyy-MM-dd)\n" )
    @RequestMapping(method = RequestMethod.GET)
    public String calculate(@RequestParam(required = true) @Min(value = 1)
                                Integer averageSalary,
                            @RequestParam(required = true, defaultValue = "NUM_DAYS")
                                CalculateTypeValues calculateTypeValues,
                            @RequestParam(required = false) @Min(value = 1)
                                Integer numDays,
                            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd")
                                LocalDate startDate,
                            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd")
                                LocalDate endDate) {

        return calculateService.calculateVacationPackage(averageSalary, calculateTypeValues, numDays,
                startDate, endDate).toString();
    }


}
