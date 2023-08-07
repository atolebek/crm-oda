package kz.tele2.crmoda.service.impl.electricity;

import kz.tele2.crmoda.dto.request.electricity.SendElectricityRequest;
import kz.tele2.crmoda.dto.response.electricity.PaidMonthsResponse;
import kz.tele2.crmoda.enums.ApplicationName;
import kz.tele2.crmoda.enums.ApplicationType;
import kz.tele2.crmoda.enums.UserType;
import kz.tele2.crmoda.model.Application;
import kz.tele2.crmoda.model.Electricity;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.model.onec.Condition;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.model.onec.Site;
import kz.tele2.crmoda.repository.*;
import kz.tele2.crmoda.service.application.ApplicationService;
import kz.tele2.crmoda.service.electricity.ElectricityService;
import kz.tele2.crmoda.util.DateFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElectricityServiceImpl implements ElectricityService {

    private final ElectricityRepository electricityRepository;
    private final CounterpartyRepository counterpartyRepository;
    private final UserRepository userRepository;
    private final ConditionRepository conditionRepository;
    private final SiteRepository siteRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;

    @Override
    public List<Electricity> getClientElectricities(String username) {
        User user = userRepository.findByUsername(username);
        Counterparty counterparty = counterpartyRepository.findByName(user.getName());
        return electricityRepository.getClientElectricities(counterparty);
    }

    @Override
    public List<PaidMonthsResponse> getPaidMonths(String username) {
        List<PaidMonthsResponse> responses = new ArrayList<>();
        User user = userRepository.findByUsername(username);
        Counterparty counterparty = counterpartyRepository.findByName(user.getName());

        List<Electricity> electricities = electricityRepository.getClientElectricities(counterparty);
        Map<LocalDate, Electricity> dateElectricityMap = electricities.stream()
                .collect(Collectors.toMap(e -> e.getStartDate(), e -> e));
        List<LocalDate> last12Months = new ArrayList<>();
        LocalDate thisMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate firstOfLast12Months = thisMonth.minusYears(1).plusMonths(1);
        for (int i = 0; i < 12; i++) {
            last12Months.add(firstOfLast12Months.plusMonths(i));
        }

        for (LocalDate l : last12Months) {
            PaidMonthsResponse response = new PaidMonthsResponse();
            Electricity e = dateElectricityMap.get(l);
            Boolean hasSentCounterReadings = e != null;
            response.setHasSentCounterReadings(hasSentCounterReadings);
            response.setStartDate(e == null ? l : e.getStartDate());
            response.setEndDate(e == null ? l.withDayOfMonth(l.lengthOfMonth()) : e.getEndDate());
            response.setYear(String.valueOf(l.getYear()));
            response.setName(DateFormatUtil.getRussianMonthName(l));
            responses.add(response);
        }
        return responses;
    }

    private Electricity sendCounterValueSingle(SendElectricityRequest request, User user, Counterparty counterparty,
                                               Site site, Condition condition, UserType userType){
        Application application = applicationService.createElectricityApplication(request, counterparty, userType);
        Electricity electricity = saveElectricity(request, condition, userType, user, counterparty, site, application);
        application.setElectricity(electricity);
        applicationRepository.save(application);
        return electricity;
    }

    private List<SendElectricityRequest> divideSendCounterMultipleMonths(SendElectricityRequest request) {
        List<SendElectricityRequest> requests = new ArrayList<>();
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        int startingCounterValue = Integer.parseInt(request.getPreviousCounterValue());
        int currentCounterValue = Integer.parseInt(request.getCounterValue());
        int delta = currentCounterValue - startingCounterValue;

        int monthsBetween = ((int) ChronoUnit.MONTHS.between(startDate, endDate));

        for (int i = 0; i <= monthsBetween; i++) {
            SendElectricityRequest singleRequest = request;
            singleRequest.setPreviousCounterValue(Integer.toString(startingCounterValue));
            if (i == monthsBetween) {
                singleRequest.setCounterValue(Integer.toString((delta/(i+1)) + delta % (i + 1)));
            } else {
                singleRequest.setCounterValue(Integer.toString(delta / (i + 1)));
            }
            singleRequest.setStartDate(startDate);
            singleRequest.setEndDate(startDate.withDayOfMonth(startDate.lengthOfMonth()));
            startDate = startDate.plusMonths(1);
            startingCounterValue = startingCounterValue + delta/(i+1);
            requests.add(singleRequest);
        }

        return requests;
    }

    @Override
    public List<Electricity> sendCounterValues(SendElectricityRequest request, String username) {
        User user = userRepository.findByUsername(username);
        Counterparty counterparty = counterpartyRepository.findByName(user.getName());
        Site site = siteRepository.getSiteByName(request.getSiteName());
        Condition condition = conditionRepository.getConditionForSign(request.getContractCode(), site, counterparty, "electricity");
        UserType userType = user.getIsEntity() ? UserType.JURIDICAL : UserType.INDIVIDUAL;

        List<Electricity> responses = new ArrayList<>();

        for (SendElectricityRequest singleRequest : divideSendCounterMultipleMonths(request)) {
            Electricity electricity = sendCounterValueSingle(singleRequest, user, counterparty, site, condition, userType);
            responses.add(electricity);
        }

        return responses;
    }

    @Override
    public Electricity getElectricity(Long rentId) {
        return electricityRepository.getById(rentId);
    }

    private Electricity saveElectricity(SendElectricityRequest request, Condition condition, UserType userType,
                                        User user, Counterparty counterparty, Site site, Application application) {
        BigDecimal tariff = new BigDecimal(request.getTariffRate());
        BigDecimal counterDelta = new BigDecimal(request.getCounterValue()).subtract(new BigDecimal(request.getPreviousCounterValue()));
        BigDecimal totalSum = tariff.multiply(counterDelta);
        Electricity electricity =
                electricityRepository.save(Electricity.builder()
                        .contractCode(condition.getContract_code())
                        .counter_value(request.getCounterValue())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .totalSum(totalSum)
                        .tariff(tariff)
                        .user_type(userType.name().toLowerCase())
                        .status("NEW")
                        .user(user)
                        .employee("")
                        .bts_detail_locality(site.getPhysicalAddress())
                        .iin(counterparty.getIdentification_code())
                        .usedKWt(counterDelta.toString())
                        .site(site)
                        .counterparty(counterparty)
                        .application(application)
                        .build());
        return electricity;
    }
}
