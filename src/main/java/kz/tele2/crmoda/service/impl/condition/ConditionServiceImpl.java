package kz.tele2.crmoda.service.impl.condition;

import kz.tele2.crmoda.dto.response.condition.ActiveConditionsResponse;
import kz.tele2.crmoda.dto.response.condition.ConditionResponse;
import kz.tele2.crmoda.enums.ApplicationType;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.model.onec.Condition;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.repository.ConditionRepository;
import kz.tele2.crmoda.repository.CounterpartyRepository;
import kz.tele2.crmoda.repository.UserRepository;
import kz.tele2.crmoda.service.condition.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionServiceImpl implements ConditionService {

    private final ConditionRepository conditionRepository;
    private final CounterpartyRepository counterpartyRepository;
    private final UserRepository userRepository;

    @Override
    public ActiveConditionsResponse getActiveConditionsForUser(String username) {
        User user = userRepository.findByUsername(username);
        Counterparty counterparty = counterpartyRepository.findByName(user.getName());
        List<Condition> rentConditions = conditionRepository.getActiveConditionsByCounterpartyAndType(counterparty, ApplicationType.RENT.toString().toLowerCase(), LocalDate.now());
        List<Condition> electricityConditions = conditionRepository.getActiveConditionsByCounterpartyAndType(counterparty, ApplicationType.ELECTRICITY.toString().toLowerCase(), LocalDate.now());

        List<ConditionResponse> rentResponse = new ArrayList<>();
        for (Condition c : rentConditions) {
            rentResponse.add(ConditionResponse.builder()
                    .siteName(c.getSite().getName())
                    .period(c.getPeriod())
                    .counterpartyName(c.getCounterparty().getName())
                    .expense_type(c.getExpense_type())
                    .start_date(c.getStart_date())
                    .end_date(c.getEnd_date())
                    .contract_code(c.getContract_code())
                    .counterparty_contract(c.getCounterparty_contract())
                    .build());
        }
        List<ConditionResponse> electricityResponse = new ArrayList<>();
        for (Condition c : electricityConditions) {
            electricityResponse.add(ConditionResponse.builder()
                    .siteName(c.getSite().getName())
                    .period(c.getPeriod())
                    .counterpartyName(c.getCounterparty().getName())
                    .expense_type(c.getExpense_type())
                    .start_date(c.getStart_date())
                    .end_date(c.getEnd_date())
                    .contract_code(c.getContract_code())
                    .counterparty_contract(c.getCounterparty_contract())
                    .build());
        }

        ActiveConditionsResponse response = new ActiveConditionsResponse();
        response.setRents(rentResponse);
        response.setElectricities(electricityResponse);
        return response;
    }
}
