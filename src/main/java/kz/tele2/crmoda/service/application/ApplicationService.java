package kz.tele2.crmoda.service.application;

import kz.tele2.crmoda.dto.request.SignDocumentRequest;
import kz.tele2.crmoda.enums.ApplicationName;
import kz.tele2.crmoda.enums.ApplicationType;
import kz.tele2.crmoda.enums.UserType;
import kz.tele2.crmoda.model.Application;
import kz.tele2.crmoda.model.onec.Condition;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.report.ReportGenerator;
import kz.tele2.crmoda.repository.ApplicationRepository;
import kz.tele2.crmoda.repository.ConditionRepository;
import kz.tele2.crmoda.repository.CounterpartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ConditionRepository conditionRepository;

    public Application createApplicationForPayment(SignDocumentRequest request, Counterparty counterparty,
                                                   UserType userType, ApplicationType type) {
        LocalDate pdfDate = userType == UserType.JURIDICAL ? request.getEndDate() : LocalDate.now();
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
                                .conditionType(type.name().toLowerCase())
                                .applicationId(request.getUserDefinedUniqueCompletionCertificateId())
                                .build());
        return application;
    }

    public byte[] renderReport(String contractCode, String bts, String contractSum, LocalDate startDate, Boolean signed) {
        Condition condition = conditionRepository.getConditionByContract_code(contractCode).get(0);
        return new ReportGenerator().generateReport(condition.getCounterparty(), bts, contractSum, startDate, signed);
    }

    public Application save(Application application) {
        return applicationRepository.save(application);
    }
}
