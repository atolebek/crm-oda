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

    @Query(value = "SELECT c FROM Condition c where c.counterparty = :counterparty and c.condition_type = 'rent'" +
            " and c.condition_status = 'Active' and c.start_date <= :date and c.end_date >= :date")
    List<Condition> findAllByCounterpartyIdNeedsToSign(Counterparty counterparty, LocalDate date);

    @Query(value = "SELECT c FROM Condition c WHERE c.condition_type = :type AND c.contract_code = :contractCode " +
            "AND c.site = :site AND c.counterparty = :counterparty")
    Condition getConditionForSign(String contractCode, Site site, Counterparty counterparty, String type);

    @Query(value = "SELECT c FROM Condition c WHERE c.counterparty = :counterparty AND c.condition_type = :type " +
            "AND c.start_date <= :now AND c.end_date > :now AND c.condition_status = 'Active' " +
            "AND c.expense_type <> 'Аренда земельного участка' AND c.contract_code IS NOT NULL")
    List<Condition> getActiveConditionsByCounterpartyAndType(Counterparty counterparty, String type, LocalDate now);

    @Query(value = "SELECT c FROM Condition c " +
            "WHERE c.counterparty = :counterparty " +
            "AND c.condition_type = :type " +
            "AND c.start_date <= :now AND c.end_date > :now " +
            "AND c.condition_status = 'Active' " +
            "AND c.contract_code = :contractCode " +
            "AND c.site = :site")
    Condition getFirstByCounterpartyAndTypeAndSiteActiveConditionsByCounterpartyAndTypeAndSite(Site site, String contractCode, Counterparty counterparty, String type, LocalDate now);

    @Query(value = "SELECT c FROM Condition c WHERE c.contract_code = :contractCode")
    List<Condition> getConditionByContract_code(String contractCode);
}
