package kz.tele2.crmoda.model;


import kz.tele2.crmoda.enums.DocumentStatus;
import kz.tele2.crmoda.model.onec.Counterparty;
import kz.tele2.crmoda.model.onec.Site;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "electricity")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Electricity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contractCode;//TODO FOREING 'Conditions'

    private String counter_value;

    private LocalDate startDate;//дата начала периода за которое мы должны денег, ориентируемся на дату последнего платежа

    private LocalDate endDate;//дата окончания периода за которое мы должны денег, ориентируемся на последний день текущего месяца

    private BigDecimal totalSum;//итоговая сумма, которую мы платим юзеру

    private BigDecimal tariff;//цена 1 ватта

    private String user_type;//тип пользователя | enum (физлицо/юрлицо) TODO ENUM

    @ManyToOne
    private Site site;//базовая станция

    @ManyToOne
    private Counterparty counterparty;//арендодатель

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;//статус обработки показания счётчиков enum(новые, просмотренные, в процессе, завершено, ошибошке) * переименовать в status

    @ManyToOne
    private User user;

    @ManyToOne
    private User employee;

    private String bts_detail_locality;//переделать на связь с таблицей sites и переименовать поле в site_id TODO FOREIGN

    private String iin;// - ИИН | varchar(12)

    private String usedKWt;// - разница между текущими и прежними показаниями счетчиков | varchar(9) * переименоват в used_kwt_delta

    private String sum_without_losses;// - cумма без потерь | varchar(9)

    private String losses;// - потери, заполняются юзером | varchar(9)

    private String clientCertificate;// - сигнатура ЭЦП-подписи юзера | jsonb * snake_case

    private String managerCertificate;// - сигнатура ЭЦП-подписи менеджера | jsonb * snake_case

    private String group_id;// - группа подписания uuid | varchar(63)

    @OneToOne
    private Application application;// - foreign key applications

    private int createdBy;//TODO FOREIGN USER

    private int updatedBy;//TODO FOREIGN USER

    private LocalDate createdAt;

    private LocalDate updatedAt;

}
