package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.onec.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<Condition, Integer> {

    Condition findByCounterpartyId(Long counterPartyId);

}
