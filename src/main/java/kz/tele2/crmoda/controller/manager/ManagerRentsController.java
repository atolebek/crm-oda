package kz.tele2.crmoda.controller.manager;

import io.swagger.annotations.Api;
import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import kz.tele2.crmoda.service.rent.ClientRentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rents/manager")
@Api(tags = "ManagerRents")
@RequiredArgsConstructor
public class ManagerRentsController {

    private final ClientRentService rentService;

    @GetMapping
    @Secured("ROLE_CLIENT")
    public List<ClientRentsResponse> getRents(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return rentService.getClientsRents(username);
    }
}
