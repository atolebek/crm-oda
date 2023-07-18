package kz.tele2.crmoda.model.onec;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sites")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String owner;//TODO ENUM KCELL TELE2

    private String code;//Site code

    private String name;//SITE NAME

    private String location;//LOCATION

    private Boolean accountantApproved;//Buhgalter odobril??

    private String priority;//TODO ENUM

    private String status;//TODO ENUM??

    private String region;//TODO FOREIGN -> regions

    private String district;//TODO FOREIGN -> regions

    private String physicalAddress;//TODO FOREGIN -> regions

    private String company;//TODO same as owner

    private String responsibleEmployee;//Kto otvechaet za etu bts
}
