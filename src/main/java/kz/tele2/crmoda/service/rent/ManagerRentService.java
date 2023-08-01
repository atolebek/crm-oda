package kz.tele2.crmoda.service.rent;

import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import kz.tele2.crmoda.dto.response.rent.ManagerRentsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManagerRentService {

    List<ManagerRentsResponse> getManagerRents(String username);
}
