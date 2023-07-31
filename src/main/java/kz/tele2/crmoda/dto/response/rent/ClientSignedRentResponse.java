package kz.tele2.crmoda.dto.response.rent;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.tele2.crmoda.model.Application;
import kz.tele2.crmoda.model.onec.Counterparty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ClientSignedRentResponse {
    private Long id;
    @JsonProperty("contract_code")
    private String contractCode;
    @JsonProperty("site_name")
    private String siteName;//OR NAME
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("counterparty_name")
    private String counterpartyName;
    @JsonProperty("total_sum")
    private BigDecimal totalSum;
    @JsonProperty("created_ad")
    private LocalDateTime createdAt;
    @JsonProperty("signed_pdf")
    private String signedPdf;
}
