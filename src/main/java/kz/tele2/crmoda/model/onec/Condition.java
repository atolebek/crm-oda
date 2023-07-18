package kz.tele2.crmoda.model.onec;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "conditions")
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate period;// - дата подписания договора | datetime * переименовать

    @ManyToOne
    private Site site;// - базовая станция | varchar(255) * переименовать в site_id

    @OneToOne
    private Counterparty counterparty;// - наш арендодатель | foreign key counterparties, int

    private String counterparty_contract;// - id контракта, приходит из 1с | varchar(255)

    private String expense_type;// - тип расхода | varchar(255) * обсудить с Байзой возможность сделать enum, непонятно что делать с префиксом, который прихдит из 1с

    private BigDecimal contract_sum;// - сумма контракта, может быть пустым | numeric(10 2) null

    private LocalDate start_date;// - дата начала действия договора | datetime

    private LocalDate end_date;// - дата окончания действия договора | datetime

    private Boolean prolongation;// - был ли пролонгирован договор | bool

    private LocalDate prolongation_end_date;// - дата окончания пролонгированного договора

    private Boolean vat_payer;// - платильщик НДС | boolean

    private Boolean iit_tax;//ИПН | boolean

    private BigDecimal sum_1;

    private Boolean include_vat_1;

    private String vat_1_rate;

    private BigDecimal sum_2;

    private Boolean include_vat_2;

    private String vat_2_rate;

    private BigDecimal sum_1_payment;

    private String bank_account_1;

    private String payment_terms_1;

    private int amount_of_prepayments_months_1;

    private BigDecimal sum_2_payment;

    private String bank_account_2;

    private String payment_terms_2;

    private int amount_of_prepayments_months_2;

    private String counterparty_for_payments_2;//TODO FOREIGN MAYBE?

    private String counterparty_payment_contract_2;//

    private BigDecimal amount_to_be_payed;

    private BigDecimal vat_sum_1;

    private BigDecimal vat_sum_2;

    private String condition_status;

    private String electricity_conditions;

    private String approver;

    private String condition_type;

    private Boolean checked;

    private Boolean checked_manager;

    private String id_data_ifrs;

    private Boolean longterm;

    private Boolean no_original_artal;

    private BigDecimal cpc_sum;

    private Boolean cpc_tax;

    private Boolean chi_charge;

    private BigDecimal chi_sum;

    private Boolean has_stage_payments;

    private String comment;

    private Boolean sum_includes_vat_1;

    private Boolean sum_includes_vat_2;

    private String bank_account_code_1;

    private String bank_account_code_2;

    private String locale;

    private int created_by;//TODO FOREIGN

    private int updated_by;//TODO FOREIGN

    private LocalDate created_at;

    private LocalDate updated_at;

    private String counterparty_code;

    private String contract_code;

}
