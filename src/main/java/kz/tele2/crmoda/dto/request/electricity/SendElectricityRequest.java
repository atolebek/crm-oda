package kz.tele2.crmoda.dto.request.electricity;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.tele2.crmoda.dto.request.SignDocumentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class SendElectricityRequest extends SignDocumentRequest {

    public SendElectricityRequest() {
        super();
    }
    public SendElectricityRequest(SendElectricityRequest request) {
        super(request);
        this.counterValue = request.getCounterValue();
        this.previousCounterValue = request.getPreviousCounterValue();
        this.tariffRate = request.getTariffRate();
    }

    @JsonProperty("counter_value")
    private String counterValue;

    @JsonProperty("prev_counter_value")
    private String previousCounterValue;

    @JsonProperty("tariff_rate")
    private String tariffRate;

}
