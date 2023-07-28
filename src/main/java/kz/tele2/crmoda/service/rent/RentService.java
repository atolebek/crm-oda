package kz.tele2.crmoda.service.rent;

import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RentService {

    List<ClientRentsResponse> getClientsRents(String username);



}
