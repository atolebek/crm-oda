package kz.tele2.crmoda.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignDocumentRequest {

    public SignDocumentRequest() {
    }

    public SignDocumentRequest(SignDocumentRequest request) {
        this.siteName = request.getSiteName();
        this.contractCode = request.getContractCode();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.userDefinedUniqueCompletionCertificateId = request.getUserDefinedUniqueCompletionCertificateId();
    }

    @JsonProperty("site_name")
    private String siteName;

    @JsonProperty("contract_code")
    private String contractCode;

    @JsonProperty("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @JsonProperty("userDefinedUniqueCompletionCertificateId")
    private String userDefinedUniqueCompletionCertificateId;
}
