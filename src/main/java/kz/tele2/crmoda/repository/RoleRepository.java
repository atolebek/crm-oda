package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.Role;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    List<Role> findAllByNameIn(List<String> names);

    @Query("SELECT r FROM Role r")
    List<Role> getAll();

    @Transactional
    @Query(value = "DELETE FROM app_user_role WHERE userId = ?1",
    nativeQuery = true)
    @Modifying
    void deleteAllUserRoles(Long userId);

}
