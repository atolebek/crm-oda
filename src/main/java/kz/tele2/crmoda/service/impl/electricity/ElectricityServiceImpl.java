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
import kz.tele2.crmoda.service.electricity.ElectricityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElectricityServiceImpl implements ElectricityService {

    private ElectricityRepository electricityRepository;
    private CounterpartyRepository counterpartyRepository;
    private UserRepository userRepository;
    private ConditionRepository conditionRepository;
    private SiteRepository siteRepository;
    private ApplicationRepository applicationRepository;

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
            response.setStartDate(e.getStartDate());
            response.setEndDate(e.getEndDate());
            response.setYear(String.valueOf(l.getYear()));
            response.setName(l.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<Electricity> sendCounterValues(SendElectricityRequest request, String username) {
        User user = userRepository.findByUsername(username);
        Counterparty counterparty = counterpartyRepository.findByName(user.getName());
        Site site = siteRepository.getSiteByName(request.getSiteName());
        Condition condition = conditionRepository.getConditionForSign(request.getContractCode(), site, counterparty, "electricity");
        UserType userType = user.getIsEntity() ? UserType.JURIDICAL : UserType.INDIVIDUAL;
        LocalDate pdfDate = user.getIsEntity() ? request.getEndDate() : LocalDate.now();
        Application application =
                applicationRepository.save(
                        Application.builder()
                                .applicationName(userType == UserType.JURIDICAL ?
                                        ApplicationName.COMPLETION_CERTIFICATE.name().toLowerCase()
                                        : ApplicationName.ACTUAL_AREA_USAGE_ACT.name().toLowerCase())
                                .signedByClient(true)
                                .signedByManager(false)
                                .syncedWith1C(false)
                                .counterparty(counterparty)
                                .pdfDate(pdfDate)
                                .hash("ABCDEF")
                                .unsignedPdf("UNSIGNED_LINK")
                                .unsignedName("UNSIGNED_NAME")
                                .signedPdf("SIGNED_LINK")
                                .signedName("SIGNED_NAME")
                                .conditionType(ApplicationType.ELECTRICITY.name().toLowerCase())
                                .applicationId(request.getUserDefinedUniqueCompletionCertificateId())
                                .build());
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
        application.setElectricity(electricity);
        applicationRepository.save(application);
        List<Electricity> response = new ArrayList<>();
        response.add(electricity);
        return response;
    }
}
