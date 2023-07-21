package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.onec.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Integer> {

    List<Condition> findAllByCounterpartyId(Long counterPartyId);

    @Query(value = "SELECT c FROM Condition c where c.counterparty = ?1 and c.condition_type = 'аренда'" +
            " and c.condition_status = 'Действующее' and c.start_date <= ?2 and c.start_date >= ?2")
    List<Condition> findAllByCounterpartyIdNeedsToSign(Long counterPartyId, LocalDateTime date);
}
