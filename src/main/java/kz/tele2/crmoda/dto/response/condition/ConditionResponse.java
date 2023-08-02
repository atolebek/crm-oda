package kz.tele2.crmoda.dto.response.condition;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ConditionResponse {

     private String siteName;
     private LocalDate period;
     private String counterpartyName;
     private String expense_type;
     private LocalDate start_date;
     private LocalDate end_date;
     private String contract_code;
     private String counterparty_contract;
}
