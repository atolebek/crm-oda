package kz.tele2.crmoda.dto.response.rent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ClientRentsResponse {

    private Long id;

    @JsonProperty("contract_code")
    private String contractCode;

    @JsonProperty("site_id")
    private Long siteId;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("total_sum")
    private BigDecimal totalSum;

    private Boolean signed;
}
