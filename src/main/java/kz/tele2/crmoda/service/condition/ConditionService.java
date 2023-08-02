package kz.tele2.crmoda.service.condition;

import kz.tele2.crmoda.dto.response.condition.ActiveConditionsResponse;
import kz.tele2.crmoda.enums.ApplicationType;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.model.onec.Condition;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public interface ConditionService {

    ActiveConditionsResponse getActiveConditionsForUser(String userName);

}
