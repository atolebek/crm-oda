package kz.tele2.crmoda.service.impl.rent;

import kz.tele2.crmoda.dto.request.SignDocumentRequest;
import kz.tele2.crmoda.dto.request.rent.SignRentRequest;
import kz.tele2.crmoda.dto.response.rent.ClientRentsResponse;
import kz.tele2.crmoda.dto.response.rent.ClientSignedRentResponse;
import kz.tele2.crmoda.enums.ApplicationName;
import kz.tele2.crmoda.enums.ApplicationType;
import kz.tele2.crmoda.enums.UserType;
import kz.tele2.crmoda.exception.CustomException;
import kz.tele2.crmoda.model.Application;
import kz.tele2.crmoda.model.Rent;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.model.onec.Condition;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.model.onec.Site;
import kz.tele2.crmoda.repository.*;
import kz.tele2.crmoda.service.application.ApplicationService;
import kz.tele2.crmoda.service.rent.ClientRentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientRentServiceImpl implements ClientRentService {

    private final UserRepository userRepository;
    private final CounterpartyRepository counterpartyRepository;
    private final ConditionRepository conditionRepository;
    private final RentRepository rentRepository;
    private final SiteRepository siteRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;

    @Override
    public List<ClientRentsResponse> getClientsRents(String username) {
        //Get user first
        User user = userRepository.findByUsername(username);

        //Find counterparty by username
        Counterparty counterparty = counterpartyRepository.findByName(user.getName());

        //Find condition for that counterparty
        List<Condition> conditions = conditionRepository.findAllByCounterpartyIdNeedsToSign(counterparty, LocalDateTime.now().toLocalDate());

        //Accumulate last 6 months
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last6Months = now.minusMonths(6).withDayOfMonth(1);

        //Container for response
        List<ClientRentsResponse> responses = new ArrayList<>();

        //Populate response
        for(Condition c : conditions){
            if (c.getContract_code() == null || c.getContract_code().isEmpty()) { continue; }
            LocalDate dateRangeStart = c.getStart_date().isAfter(last6Months.toLocalDate()) ? c.getStart_date() : last6Months.toLocalDate();
            List<LocalDate> listOf6Months = new ArrayList<>();
            while (dateRangeStart.isBefore(now.toLocalDate().withDayOfMonth(1))) {
                dateRangeStart = dateRangeStart.withDayOfMonth(1);
                listOf6Months.add(dateRangeStart);
                dateRangeStart = dateRangeStart.plusMonths(1);
            }

            List<Rent> signedRents = rentRepository.findAllByCounterparty(counterparty);

//            List<Rent> last6MonthRents = signedRents.stream()
//                    .filter(x -> x.getContractCode() == c.getContract_code())
//                    .filter(x -> x.getStartDate().isAfter(last6Months.toLocalDate()))
//                    .collect(Collectors.toList());

            Map<LocalDate, Rent> startDateSignedRentsMap = signedRents.stream().collect(Collectors.toMap(r -> r.getStartDate(), r -> r));

            if (!user.getIsEntity()) {
                for (LocalDate l : listOf6Months) {
                    if (startDateSignedRentsMap.get(l) == null) {
                        ClientRentsResponse rentsResponse = ClientRentsResponse.builder()
                                .startDate(l)
                                .contractCode(c.getContract_code())
                                .siteName(c.getSite().getName())
                                .totalSum(c.getSum_1())
                                .signed(false)
                                .build();
                        responses.add(rentsResponse);
                    }
                }
            }
            for (Rent r : signedRents) {
                ClientRentsResponse rentsResponse = ClientRentsResponse.builder()
                        .id(r.getId())
                        .startDate(r.getStartDate())
                        .contractCode(r.getContractCode())
                        .siteName(r.getSite().getName())
                        .totalSum(r.getTotalSum())
                        .signed(true)
                        .build();
                responses.add(rentsResponse);
            }
        }
        return responses;
    }

    @Override
    public ClientSignedRentResponse getSignedRent(Long rentId) {
        Rent rent = rentRepository.getById(rentId);
        if (rent == null) {
            throw new CustomException("Rent with ID " + rentId + "does not exist", HttpStatus.NOT_FOUND);
        }
        ClientSignedRentResponse response = ClientSignedRentResponse.builder()
                .id(rent.getId())
                .contractCode(rent.getContractCode())
                .siteName(rent.getSite().getName())
                .startDate(rent.getStartDate())
                .counterpartyName(rent.getCounterparty().getName())
                .totalSum(rent.getTotalSum())
                .createdAt(rent.getCreatedAt())
                .signedPdf(rent.getApplication().getSignedPdf())
                .build();
        return response;
    }

    @Override
    public List<Rent> signRent(SignDocumentRequest request) {
        List<Rent> response = new ArrayList<>();
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username);

        Counterparty counterparty = counterpartyRepository.findByName(user.getName());

        Site site = siteRepository.getSiteByName(request.getSiteName());

        Condition condition = conditionRepository.getConditionForSign(request.getContractCode(), site, counterparty, "rent");

        List<LocalDate> dateRange = new ArrayList<>();

        while (startDate.isBefore(endDate)) {
            dateRange.add(startDate);
            startDate = startDate.plusMonths(1);
        }

        List<Rent> existingRents = rentRepository.findAllSignedRents(counterparty, site, request.getContractCode(), dateRange);

        List<LocalDate> existingDates = existingRents.stream()
                .map(r -> r.getStartDate())
                .collect(Collectors.toList());
        dateRange.removeAll(existingDates);

        if (dateRange.size() == 0) {
            throw new CustomException("You have already signed rent for this period", HttpStatus.CONFLICT);
        }

        UserType userType = user.getIsEntity() ? UserType.JURIDICAL : UserType.INDIVIDUAL;
        for (LocalDate newRentDate : dateRange) {
            Application application = applicationService.createApplicationForPayment(request, counterparty, userType, ApplicationType.RENT);
            Rent rent = createRent(site, user, application, newRentDate, userType, condition, counterparty, null, Integer.parseInt(request.getUserDefinedUniqueCompletionCertificateId()));
            application.setRent(rent);
            applicationService.save(application);
            response.add(rent);
        }
        return response;
    }

    public Rent createRent(Site site, User user, Application application, LocalDate newRentDate,
                            UserType userType, Condition condition,
                            Counterparty counterparty, String groupid, int completionCertificateNumber) {
        LocalDate newRentEndDate = newRentDate.withDayOfMonth(newRentDate.lengthOfMonth());

        return rentRepository.save(
                Rent.builder()
                        .site(site)
                        .hasError(false)
                        .errorMessage(null)
                        .archived(false)
                        .status("NEW")
                        .onCompletion(false)
                        .employee(user.getCurator())
                        .application(application)
                        .group_id(groupid)
                        .startDate(newRentDate)
                        .endDate(newRentEndDate)
                        .totalSum(condition.getSum_1() == null ? condition.getSum_2() : condition.getSum_1())
                        .userType(userType.name())
                        .contractCode(condition.getContract_code())
                        .counterparty(counterparty)
                        .createdAt(LocalDateTime.now())
                        .completionCertificate(completionCertificateNumber)
                        .build()
        );
    }
}
