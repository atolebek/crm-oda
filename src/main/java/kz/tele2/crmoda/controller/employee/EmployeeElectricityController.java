package kz.tele2.crmoda.controller.employee;

import io.swagger.annotations.Api;
import kz.tele2.crmoda.model.Electricity;
import kz.tele2.crmoda.service.electricity.ElectricityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/electricity/employee")
@Api(tags = "Employee Electricity")
@RequiredArgsConstructor
public class EmployeeElectricityController {

    private final ElectricityService electricityService;

    @GetMapping("/active")
    public List<Electricity> getEmployeeElectricites() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return electricityService.getEmployeeElectricities(username);
    }

    @GetMapping("/{id}")
    public Electricity getEmployeeElectricity(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return electricityService.getElectricity(id);
    }

}
