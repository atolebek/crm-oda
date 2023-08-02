package kz.tele2.crmoda.controller.client;

import io.swagger.annotations.Api;
import kz.tele2.crmoda.dto.response.condition.ActiveConditionsResponse;
import kz.tele2.crmoda.dto.response.condition.ConditionResponse;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.service.condition.ConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conditions/client")
@Api(tags = "Conditions")
@RequiredArgsConstructor
public class ClientConditionController {

    private final ConditionService conditionService;

    @GetMapping("/active")
    @Secured("ROLE_CLIENT")
    public ActiveConditionsResponse activeConditions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return conditionService.getActiveConditionsForUser(username);
    }

}
