package kz.tele2.crmoda.dto.response.auth;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import kz.tele2.crmoda.model.User;
import lombok.Data;
import kz.tele2.crmoda.model.Role;

@Data
public class UserResponseDTO {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ApiModelProperty(position = 0)
  private String jwt;
  @ApiModelProperty(position = 1)
  private Long id;
  @ApiModelProperty(position = 2)
  private String username;
  @ApiModelProperty(position = 3)
  private String email;
  @ApiModelProperty(position = 4)
  private Boolean confirmed;
  @ApiModelProperty(position = 5)
  private Boolean blocked;
  @JsonProperty("role")
  @ApiModelProperty(position = 6)
  List<Role> roles;
  @JsonProperty("name")
  @ApiModelProperty(position = 7)
  private String name;
  @ApiModelProperty(position = 8)
  private User curator;
  @JsonProperty("terms_of_use")
  @ApiModelProperty(position = 9)
  private Boolean termsOfUse;
  @ApiModelProperty(position = 10)
  private List<String> electricities;
  @JsonProperty("is_entity")
  @ApiModelProperty(position = 11)
  private Boolean isEntity;
  @JsonProperty("phone_number")
  @ApiModelProperty(position = 12)
  private String phoneNumber;
  @JsonProperty("employee_curator")
  @ApiModelProperty(position = 13)
  private User employeeCurator;
}
