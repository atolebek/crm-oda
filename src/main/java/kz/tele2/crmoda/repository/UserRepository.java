package kz.tele2.crmoda.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import kz.tele2.crmoda.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByUsername(String username);

  User findByUsername(String username);

  @Transactional
  void deleteByUsername(String username);

}
