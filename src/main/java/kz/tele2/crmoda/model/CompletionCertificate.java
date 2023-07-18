package kz.tele2.crmoda.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CompletionCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bin;

    private BigDecimal totalSum;

    private String state1C;

    private Boolean foundMatch;

    private String createdBy;

    private String updatedBy;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String registrationNumber;

    private LocalDate completionDate;

    private String counterParty; //TODO foreign

    private String bts; //TODO foreign site_id
}
