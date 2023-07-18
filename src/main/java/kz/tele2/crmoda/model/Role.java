package kz.tele2.crmoda.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role implements Serializable, GrantedAuthority {

  public static final Role ROLE_ADMIN = new Role("ROLE_ADMIN", null, null);
  public static final Role ROLE_CLIENT = new Role("ROLE_CLIENT", null, null);
  public static final Role ROLE_MANAGER = new Role("ROLE_MANAGER", null, null);

  public Role(String name, String description, String type){
    this.name = name.toUpperCase();
    this.description = description;
    this.type = type;
  }

  public Role(String name){
    this.name = name.toUpperCase();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  private String description;

  private String type;

  @Override
  public String getAuthority(){
    return this.name;
  }

}
