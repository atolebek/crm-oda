package kz.tele2.crmoda.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import kz.tele2.crmoda.model.onec.Counterparty;
import lombok.*;

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

    @ManyToOne
    private Counterparty counterparty;//TODO foreign key

    private LocalDate pdfDate;

    private String applicationName;//TODO enum

    private String applicationId;

    private String conditionType;//TODO enum

    @OneToOne
    @JsonBackReference
    @ToString.Exclude
    private Rent rent;//TODO foreign key

    @OneToOne
    @JsonBackReference
    @ToString.Exclude
    private Electricity electricity;//TODO foreign key

}
