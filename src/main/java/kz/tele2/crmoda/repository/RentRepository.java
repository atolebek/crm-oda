package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.Rent;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.model.onec.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {

    List<Rent> findAllByCounterparty(Counterparty counterparty);

    @Query(value = "SELECT r FROM Rent r WHERE r.counterparty = :counterparty AND r.site = :site " +
            "AND r.contractCode = :contractCode AND r.startDate IN :startDates")
    List<Rent> findAllSignedRents(Counterparty counterparty, Site site, String contractCode, List<LocalDate> startDates);
}
