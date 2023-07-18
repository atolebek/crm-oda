package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    List<Role> findAllByNameIn(List<String> names);

}
