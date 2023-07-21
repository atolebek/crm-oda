package kz.tele2.crmoda.controller.client;

import io.swagger.annotations.Api;
import kz.tele2.crmoda.configuration.Constants;
import kz.tele2.crmoda.model.Rent;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.model.onec.Condition;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.repository.ConditionRepository;
import kz.tele2.crmoda.repository.CounterpartyRepository;
import kz.tele2.crmoda.repository.RentRepository;
import kz.tele2.crmoda.repository.UserRepository;
import kz.tele2.crmoda.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rents/client")
@Api(tags = "Rents")
@RequiredArgsConstructor
public class ClientRentsController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final RentRepository rentRepository;

    private final CounterpartyRepository counterpartyRepository;

    private final ConditionRepository conditionRepository;

    @RequestMapping("GET")
    public String getRents(@RequestHeader(value = Constants.USER_ID_HEADER) Long userId){
        User user = userRepository.getById(userId);

        Counterparty counterparty = counterpartyRepository.findByName(user.getName());

        List<Condition> conditions = conditionRepository.findAllByCounterpartyIdNeedsToSign(counterparty.getId(), LocalDateTime.now());

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime last6Months = now.minusMonths(6).withDayOfMonth(1);

        for(Condition c : conditions){
            if (c.getContract_code() == null || c.getContract_code().isEmpty()) { continue; }

            LocalDate dateRangeStart = c.getStart_date().isAfter(last6Months.toLocalDate()) ? c.getStart_date() : last6Months.toLocalDate();

            List<LocalDate> listOf6Months = new ArrayList<>();

            while (dateRangeStart.isBefore(now.toLocalDate().withDayOfMonth(1)));

        }

        List<Rent> rents = rentRepository.findAllByCounterparty(counterparty.getId());



        return "";
    }

}
