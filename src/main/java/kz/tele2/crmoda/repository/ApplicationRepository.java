package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.enums.ApplicationType;
import kz.tele2.crmoda.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Query(value = "SELECT a FROM Application a WHERE a.conditionType = :applicationType" +
            " AND a.signedByManager = FALSE AND a.signedByClient = TRUE ")
    List<Application> findAllForManager(String applicationType);

}
