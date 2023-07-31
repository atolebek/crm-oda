package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.onec.Condition;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.model.onec.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Integer> {

    List<Condition> findAllByCounterpartyId(Long counterPartyId);

    @Query(value = "SELECT c FROM Condition c where c.counterparty = :counterparty and c.condition_type = 'аренда'" +
            " and c.condition_status = 'Действующее' and c.start_date <= :date and c.end_date >= :date")
    List<Condition> findAllByCounterpartyIdNeedsToSign(Counterparty counterparty, LocalDate date);

    @Query(value = "SELECT c FROM Condition c WHERE c.condition_type = 'аренда' AND c.contract_code = :contractCode " +
            "AND c.site = :site AND c.counterparty = :counterparty")
    Condition getConditionForSignRent(String contractCode, Site site, Counterparty counterparty);
}
