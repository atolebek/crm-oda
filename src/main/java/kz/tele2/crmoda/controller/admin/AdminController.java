package kz.tele2.crmoda.controller.admin;

import io.swagger.annotations.Api;
import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import kz.tele2.crmoda.model.Electricity;
import kz.tele2.crmoda.model.Rent;
import kz.tele2.crmoda.repository.ElectricityRepository;
import kz.tele2.crmoda.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Api(tags = "Rents")
@RequiredArgsConstructor
public class AdminController {

    private final RentRepository rentRepository;
    private final ElectricityRepository electricityRepository;

    @GetMapping("/rents")
    public List<Rent> getRents(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return rentRepository.findAll();
    }

    @GetMapping("/rents/{id}")
    public Rent getRent(@PathVariable Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return rentRepository.getById(id);
    }

    @GetMapping("/electricities")
    public List<Electricity> getElectricities(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return electricityRepository.findAll();
    }

    @GetMapping("/electricities/{id}")
    public Electricity getElectricity(@PathVariable Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return electricityRepository.getById(id);
    }
}
