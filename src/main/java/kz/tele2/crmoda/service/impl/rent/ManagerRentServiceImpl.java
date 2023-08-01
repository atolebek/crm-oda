package kz.tele2.crmoda.service.impl.rent;

import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import kz.tele2.crmoda.dto.response.rent.ManagerRentsResponse;
import kz.tele2.crmoda.enums.ApplicationType;
import kz.tele2.crmoda.model.Application;
import kz.tele2.crmoda.model.Rent;
import kz.tele2.crmoda.repository.ApplicationRepository;
import kz.tele2.crmoda.repository.RentRepository;
import kz.tele2.crmoda.service.rent.ManagerRentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ManagerRentServiceImpl implements ManagerRentService {

    private final RentRepository rentRepository;

    private final ApplicationRepository applicationRepository;

    @Override
    public List<ManagerRentsResponse> getManagerRents(String username) {

        List<Application> applications = applicationRepository.findAllForManager(ApplicationType.RENT.name().toLowerCase());
        List<ManagerRentsResponse> response = new ArrayList<>();


        for (Application application : applications) {
            Rent rent = application.getRent();
            response.add(
                    ManagerRentsResponse.builder()
                            .applicationId(application.getId())
                            .signedPdf(application.getSignedPdf())
                            .rent(
                                    ManagerRentsResponse.RentDto.builder()
                                            .rentId(rent.getId())
                                            .contractCode(rent.getContractCode())
                                            .site(rent.getSite().getName())
                                            .startDate(rent.getStartDate())
                                            .counterparty(rent.getCounterparty().getName())
                                            .totalSum(rent.getTotalSum())
                                            .status(rent.getStatus())
                                            .build()
                            ).build()
            );
        }

        return response;
    }
}
