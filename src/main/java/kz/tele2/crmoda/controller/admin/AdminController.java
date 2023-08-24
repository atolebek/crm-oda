package kz.tele2.crmoda.controller.admin;

import io.swagger.annotations.Api;
import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import kz.tele2.crmoda.model.Rent;
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
@RequestMapping("/admin/rents")
@Api(tags = "Rents")
@RequiredArgsConstructor
public class AdminController {

    private final RentRepository rentRepository;

    @GetMapping
    public List<Rent> getRents(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return rentRepository.findAll();
    }

    @GetMapping
    public Rent getRent(@PathVariable Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return rentRepository.getById(id);
    }

}
