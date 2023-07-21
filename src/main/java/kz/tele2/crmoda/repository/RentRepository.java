package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {

    List<Rent> findAllByCounterparty(Long counterPartyId);

}
