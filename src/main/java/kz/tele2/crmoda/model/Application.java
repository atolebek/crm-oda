package kz.tele2.crmoda.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "applications")
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

    private String counterpartyId;//TODO foreign key

    private LocalDate pdfDate;

    private String applicationName;//TODO enum

    private String applicationId;

    private String conditionType;//TODO enum

    private String rentId;//TODO foreign key

    private String electricityId;//TODO foreign key

}
