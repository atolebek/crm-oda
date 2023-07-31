package kz.tele2.crmoda.controller.client;

import io.swagger.annotations.Api;
import kz.tele2.crmoda.dto.request.rent.SignRentRequest;
import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import kz.tele2.crmoda.dto.response.rent.ClientSignedRentResponse;
import kz.tele2.crmoda.model.Rent;
import kz.tele2.crmoda.service.rent.ClientRentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rents/client")
@Api(tags = "Rents")
@RequiredArgsConstructor
public class ClientRentsController {

    private final ClientRentService rentService;

    @GetMapping
    @Secured("ROLE_CLIENT")
    public List<ClientRentsResponse> getRents(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return rentService.getClientsRents(username);
    }

    @GetMapping("/{rentId}")
    @Secured("ROLE_CLIENT")
    public ClientSignedRentResponse getSignedRent(@PathVariable("rentId") Long rentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return rentService.getSignedRent(rentId);
    }

    @PostMapping("/sign")
    public List<Rent> signRentContract(SignRentRequest request) {
        return rentService.signRent(request);
    }

}
