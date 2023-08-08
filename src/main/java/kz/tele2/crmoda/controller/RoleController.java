package kz.tele2.crmoda.controller;

import kz.tele2.crmoda.dto.request.role.CreateRoleRequest;
import kz.tele2.crmoda.exception.CustomException;
import kz.tele2.crmoda.model.Role;
import kz.tele2.crmoda.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/roles")
public class RoleController {

    private RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Role createRole(@RequestBody CreateRoleRequest request){
        Role role = roleRepository.findByName(request.getName().toUpperCase());
        if (role != null) {
            throw new CustomException("Role with name " + request.getName() + " already exists", HttpStatus.CONFLICT);
        }

        if (!request.getName().toUpperCase().startsWith("ROLE_")) {
            throw new CustomException("Role must start with prefix \"ROLE_\"", HttpStatus.BAD_REQUEST);
        }

        Role newRole = new Role(request.getName(), request.getDescription(), request.getType());
        return roleRepository.save(newRole);
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<Role> getAllRoles(){
        return roleRepository.getAll();
    }

//    public Role deleteRole(){
//
//    } TODO delete role

}
