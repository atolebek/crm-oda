package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.Electricity;
import kz.tele2.crmoda.model.onec.Counterparty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ElectricityRepository extends JpaRepository<Electricity, Long> {

    @Query(value = "SELECT e FROM Electricity e WHERE e.counterparty = :counterparty")
    List<Electricity> getClientElectricities(Counterparty counterparty);

    List<Electricity> getElectricitiesByCounterpartyAndStartDateInOrderByStartDateAsc(Counterparty counterparty, List<LocalDate> startDates);
}
