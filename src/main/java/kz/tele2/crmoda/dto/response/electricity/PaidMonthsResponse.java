package kz.tele2.crmoda.dto.response.electricity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaidMonthsResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean hasSentCounterReadings;
    private String name;
    private String year;
}
