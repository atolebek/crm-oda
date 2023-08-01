package kz.tele2.crmoda.dto.response.rent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerRentsResponse {

    private Long applicationId;
    private String signedPdf;
    private RentDto rent;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RentDto {
        private Long rentId;
        private String contractCode;
        private String site;
        private LocalDate startDate;
        private String counterparty;
        private BigDecimal totalSum;
        private String status;
    }

}
