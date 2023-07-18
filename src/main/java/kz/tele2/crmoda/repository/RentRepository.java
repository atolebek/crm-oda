package kz.tele2.crmoda.repository;

import kz.tele2.crmoda.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, Long> {

    Rent findByCounterparty(Long counterPartyId);

}
