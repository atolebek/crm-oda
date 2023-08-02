package kz.tele2.crmoda.controller.client;

import io.swagger.annotations.Api;
import kz.tele2.crmoda.dto.request.electricity.SendElectricityRequest;
import kz.tele2.crmoda.dto.response.electricity.PaidMonthsResponse;
import kz.tele2.crmoda.model.Electricity;
import kz.tele2.crmoda.service.electricity.ElectricityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/electricity/client")
@Api(tags = "Client Electricity")
@RequiredArgsConstructor
public class ClientElectricityController {

    private ElectricityService electricityService;

    @GetMapping("/mine")
    public List<Electricity> getClientElectricities() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return electricityService.getClientElectricities(username);
    }

    @GetMapping("/payedMonths")
    public List<PaidMonthsResponse> getPaidMonths() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return electricityService.getPaidMonths(username);
    }

    @PostMapping("/send")
    public List<Electricity> sendCounterValues(@RequestBody SendElectricityRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return electricityService.sendCounterValues(request, username);
    }

}
