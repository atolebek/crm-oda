package kz.tele2.crmoda.service.rent;

import kz.tele2.crmoda.dto.request.SignDocumentRequest;
import kz.tele2.crmoda.dto.request.rent.SignRentRequest;
import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import kz.tele2.crmoda.dto.response.rent.ClientSignedRentResponse;
import kz.tele2.crmoda.enums.UserType;
import kz.tele2.crmoda.model.Application;
import kz.tele2.crmoda.model.Rent;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.model.onec.Condition;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.model.onec.Site;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ClientRentService {

    List<ClientRentsResponse> getClientsRents(String username);

    ClientSignedRentResponse getSignedRent(Long rentId);

    List<Rent> signRent(SignDocumentRequest request);

    Rent createRent(Site site, User user, Application application, LocalDate newRentDate,
                  UserType userType, Condition condition,
                  Counterparty counterparty, String groupid, int completionCertificateNumber);

}
