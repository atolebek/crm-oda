package kz.tele2.crmoda.model.onec;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contract;
    private String owner;
    private String parent;
    private String code;
    private String name;
    private String currency;
    private String settlement;
    private Boolean settlements_based_on_documents;
    private String contract_type;
    private String conditions_type;
    private LocalDate contract_dt;
    private Boolean hold_reserves_without_paying_limited_time;
    private BigDecimal allowable_amount_of_debt;
    private int allowable_number_of_days;
    private String comment;
    private Boolean control_of_client_funds;
    private Boolean control_number_of_days_debt;
    private String contract_number;
    private Boolean separate_accounting_of_goods_on_the_buyer_ordered;
    private String organization;
    private String main_project;
    private BigDecimal percentage_of_commission;
    private BigDecimal percentage_prepaid;
    private Boolean calculations_in_conventional_units;
    private String commission_fee_calculation_method;
    private LocalDate validity;
    private String price_type;
    private Boolean agency_vat_accounting;
    private String main_article_of_funds_movement;
    private String mutual_settlement_type;
    private String number_of_days_reserve_without_payment;
    private Boolean debt_carryover;
    private BigDecimal av_knp;
    private BigDecimal av_kbk;
    private String av_main_article_of_refunds_movements;
    private Boolean av_exclude_from_reconciliations;
    private Boolean av_sent_to_berkut;
    private Boolean t2_exchange_rate_recalculation;
    private String t2_edo_code;
    private String t2_edo_type;
    private String days_type;
    private Boolean av_allow_application_forming;
    private String av_region;
    private Boolean on_approval;
    private Boolean joint_activity_contract;
    private String t2_discounted_price_type;
    private String counterparty_contract_number;
    private LocalDate counterparty_contract_date;
    private String terms_of_payment;
    private String terms_of_delivery;
    private String locale;
    private int created_by;//TODO FOREIGN
    private int updated_by;//TODO FOREIGN
    private LocalDate created_at;
    private LocalDate updated_at;
}
