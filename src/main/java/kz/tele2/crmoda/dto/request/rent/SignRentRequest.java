package kz.tele2.crmoda.dto.request.rent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignRentRequest {

    @JsonProperty("site_name")
    private String siteName;

    @JsonProperty("contract_code")
    private String contractCode;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    private String userDefinedUniqueCompletionCertificateId;
}
