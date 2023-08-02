package kz.tele2.crmoda.dto.request.electricity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SendElectricityRequest {

    private LocalDate startDate;
    private LocalDate endDate;
    private String contractCode;
    private String counterValue;
    private String previousCounterValue;
    private String tariffRate;
    private String siteName;
    private String userDefinedUniqueCompletionCertificateId;

}
