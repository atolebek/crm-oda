package kz.tele2.crmoda.model;

import kz.tele2.crmoda.model.onec.Counterparty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "applications")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String signedPdf;

    private String unsignedPdf;

    private boolean signedByClient;

    private boolean signedByManager;

    private boolean syncedWith1C;

    private String signedName;

    private String unsignedName;

    private String hash;

    private Counterparty counterparty;//TODO foreign key

    private LocalDate pdfDate;

    private String applicationName;//TODO enum

    private String applicationId;

    private String conditionType;//TODO enum

    private String rentId;//TODO foreign key

    private String electricityId;//TODO foreign key

}
