package kz.tele2.crmoda.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.model.onec.Site;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rents")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractCode;//          varchar(255)

    @ManyToOne
    private Site site;// - базовая станция | varchar(255) * переименовать в site_id

    private LocalDate startDate;//

    private LocalDate endDate;//

    @ManyToOne
    private Counterparty counterparty;//

    private BigDecimal totalSum;//              numeric(10 2)

    private String userType;//

    private Boolean hasError;

    private String errorMessage;//

    private int completionCertificate;// integer

    private int createdBy;//TODO FOREIGN

    private int updatedBy;//TODO FOREIGN

    private LocalDateTime createdAt;//

    private LocalDateTime updatedAt;//

    private int employee;//TODO FOREIGN USER

    private Boolean archived;//

    private String status;//TODO ENUM STATUS                  varchar(255)

    private Boolean onCompletion;

    private String clientCertificate;//      jsonb

    private String managerCertificate;//     jsonb

    private String group_id;//               varchar(255)

    @OneToOne
    @JsonManagedReference
    private Application application;//

}
