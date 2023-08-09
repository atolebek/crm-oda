package kz.tele2.crmoda.repository;

import javax.transaction.Transactional;

import kz.tele2.crmoda.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import kz.tele2.crmoda.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByUsername(String username);

  User findByUsername(String username);

  @Transactional
  void deleteByUsername(String username);

  @Query("SELECT u FROM User u ")
  List<User> getAllUsers();

  int countUsersByRolesIn(List<Role> roles);
}
