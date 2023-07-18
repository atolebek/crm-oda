package kz.tele2.crmoda.model.onec;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "counterparties")
public class Counterparty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String parent;
    private String name;
    private String main_counterparty;
    private LocalDate vat_certificate_dt;
    private String identity_document;
    private String identification_code;
    private Boolean individual_entrepreneur_lawyer_private_notary;
    private int kbe;
    private String okpo_code;
    private String comment;
    private String full_name;
    private String tax_registration_number_in_country_of_residence;
    private String vat_certificate_number;
    private String primary_bank_account;
    private String primary_counterparty_contract;
    private Boolean buyer;
    private Boolean provider;
    private String rnn;
    private String vat_certificate_series;
    private String sik;
    private String country_of_residence;
    private String physical_person;
    private String individual_or_entity;
    private String counterparty_access_group;
    private Boolean specify_details_of_parent_organization_inside_invoice;
    private Boolean distributor;
    private String distributor_warehouse;
    private String av_transit_warehouse_for_ws;
    private Boolean av_not_involved_services_budget;
    private Boolean state_institution;
    private Boolean retail_buyer;
    private Boolean leading_counterparty;
    private Boolean t2_need_to_sign_codex;
    private Boolean t2_codex_signed;
    private Boolean t2_entity_with_state_participation;
    private Boolean t2_interconnect_roaming;
    private Boolean t2_needs_sd_approval;
    private String t2_address;
    private Boolean t2_do_not_use;
    private String registration_type;
    private String code;
    private int created_by;
    private int updated_by;
    private LocalDate created_at;
    private LocalDate updated_at;

}
