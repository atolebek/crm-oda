package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.onec.Counterparty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterpartyRepository extends JpaRepository<Counterparty, Long> {

    Counterparty findByName(String name);

}
