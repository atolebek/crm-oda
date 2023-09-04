package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.onec.Counterparty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CounterpartyRepository extends JpaRepository<Counterparty, Long> {

    Counterparty findByName(String name);

    Counterparty findFirstByCode(String code);

}
