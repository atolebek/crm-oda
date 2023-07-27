package kz.tele2.crmoda.controller.client;

import io.swagger.annotations.Api;
import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import kz.tele2.crmoda.service.rent.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rents/client")
@Api(tags = "Rents")
@RequiredArgsConstructor
public class ClientRentsController {

    private final RentService rentService;

    @GetMapping
    @Secured("ROLE_CLIENT")
    public List<ClientRentsResponse> getRents(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return rentService.getClientsRents(username);
    }

}
