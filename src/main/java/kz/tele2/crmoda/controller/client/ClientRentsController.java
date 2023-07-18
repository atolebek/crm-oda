package kz.tele2.crmoda.controller.client;

import io.swagger.annotations.Api;
import kz.tele2.crmoda.configuration.Constants;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.repository.CounterpartyRepository;
import kz.tele2.crmoda.repository.RentRepository;
import kz.tele2.crmoda.repository.UserRepository;
import kz.tele2.crmoda.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client/rents")
@Api(tags = "Loans")
@RequiredArgsConstructor
public class ClientRentsController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final RentRepository rentRepository;

    private final CounterpartyRepository counterpartyRepository;

    @RequestMapping("GET")
    public String getRents(@RequestHeader(value = Constants.USER_ID_HEADER) Long userId){
        User user = userRepository.getById(userId);

        Counterparty counterparty = counterpartyRepository.findByName(user.getName());

        rentRepository.findByCounterparty(counterparty.getId());



        return "";
    }

}
