package kz.tele2.crmoda.dto.response.condition;

import kz.tele2.crmoda.model.onec.Condition;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ActiveConditionsResponse {
    private List<ConditionResponse> rents = new ArrayList<>();
    private List<ConditionResponse> electricities = new ArrayList<>();
}
