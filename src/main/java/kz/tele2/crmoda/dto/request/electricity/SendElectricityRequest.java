package kz.tele2.crmoda.dto.request.electricity;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.tele2.crmoda.dto.request.SignDocumentRequest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SendElectricityRequest extends SignDocumentRequest {

    @JsonProperty("counter_value")
    private String counterValue;

    @JsonProperty("prev_counter_value")
    private String previousCounterValue;

    @JsonProperty("tariff_rate")
    private String tariffRate;

}
