package kz.tele2.crmoda.controller.client;

import io.swagger.annotations.Api;
import kz.tele2.crmoda.model.Electricity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/electricity/client")
@Api(tags = "Client Electricity")
@RequiredArgsConstructor
public class ClientElectricityController {

    @GetMapping("/mine")
    public Electricity getClientElectricities() {


        return null;
    }

}
