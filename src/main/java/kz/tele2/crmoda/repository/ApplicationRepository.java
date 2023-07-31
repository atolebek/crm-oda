package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
