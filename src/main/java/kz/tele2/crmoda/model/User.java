package kz.tele2.crmoda.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
  @Column(unique = true, nullable = false)
  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  @Size(min = 8, message = "Minimum password length: 8 characters")
  private String password;

  @Column(unique = false, nullable = false)
  private Boolean confirmed = true;

  @Column(unique = false, nullable = false)
  private Boolean blocked = false;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.DETACH})
  @JoinTable(name = "app_user_role", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "roleid"))
  private List<Role> roles;

  @Column
  private String name;

  @Column
  private int curator;

  @Column(nullable = false)
  private Boolean termsOfUse = false;

  @Column
  private Boolean isEntity;

  @Column
  private String phoneNumber;

  @Column
  private int employeeCurator;

  @Column
  private Boolean isDeleted;
}