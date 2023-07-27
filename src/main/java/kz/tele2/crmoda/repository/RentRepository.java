package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.Rent;
import kz.tele2.crmoda.model.onec.Counterparty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {

    List<Rent> findAllByCounterparty(Counterparty counterparty);

    List<Rent> findAllByCounterpartyAndContractCodeAndStartDateGreaterThanEqual(Counterparty counterparty, String contractCode, LocalDate last6Months);


}
