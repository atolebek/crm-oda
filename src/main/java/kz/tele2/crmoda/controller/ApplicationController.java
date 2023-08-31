package kz.tele2.crmoda.controller;

import io.swagger.annotations.*;
import kz.tele2.crmoda.service.application.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/application")
@Api(tags = "Authorization")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/render")
    public byte[] render(@RequestParam("contract_code") String contractCode, @RequestParam String bts, @RequestParam("contract_sum") String contractSum, @RequestParam("start_date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate, @RequestParam Boolean signed) {
        return applicationService.renderReport(contractCode, bts, contractSum, startDate, signed);
    }

}
