package kz.tele2.crmoda.service.rent;

import kz.tele2.crmoda.dto.request.rent.SignRentRequest;
import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import kz.tele2.crmoda.dto.response.rent.ClientSignedRentResponse;
import kz.tele2.crmoda.model.Rent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientRentService {

    List<ClientRentsResponse> getClientsRents(String username);

    ClientSignedRentResponse getSignedRent(Long rentId);

    List<Rent> signRent(SignRentRequest request);

}
