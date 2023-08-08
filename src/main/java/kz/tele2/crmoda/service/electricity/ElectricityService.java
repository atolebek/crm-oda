package kz.tele2.crmoda.service.electricity;

import kz.tele2.crmoda.dto.request.SignDocumentRequest;
import kz.tele2.crmoda.dto.request.electricity.SendElectricityRequest;
import kz.tele2.crmoda.dto.response.electricity.PaidMonthsResponse;
import kz.tele2.crmoda.model.Electricity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ElectricityService {

    List<Electricity> getClientElectricities(String username);

    List<PaidMonthsResponse> getPaidMonths(String username);

    List<Electricity> sendCounterValues(SendElectricityRequest request, String username);

    Electricity getElectricity(Long rentId);
}
