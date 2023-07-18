package kz.tele2.crmoda.service;

import javax.servlet.http.HttpServletRequest;

import kz.tele2.crmoda.dto.request.auth.UserDataDTO;
import kz.tele2.crmoda.dto.response.auth.UserResponseDTO;
import kz.tele2.crmoda.exception.CustomException;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.model.Role;
import kz.tele2.crmoda.repository.RoleRepository;
import kz.tele2.crmoda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kz.tele2.crmoda.security.JwtTokenProvider;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final ModelMapper modelMapper;
  private final RoleRepository roleRepository;

  public UserResponseDTO localLogin(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      User user = userRepository.findByUsername(username);
      String jwtToken = jwtTokenProvider.createToken(username, user.getRoles());
      UserResponseDTO response = new UserResponseDTO();
      List<Role> roles = new ArrayList<>();
      Role r1 = new Role("ROLE_ADMIN");
      roles.add(r1);
      response.setJwt(jwtToken);
      response.setId(user.getId());
      response.setUsername(user.getUsername());
      response.setEmail(user.getEmail());
      response.setConfirmed(user.getConfirmed());
      response.setBlocked(user.getBlocked());
      response.setRoles(user.getRoles());
      response.setName(user.getName());
      response.setCurator(null);
      response.setTermsOfUse(null);
      response.setElectricities(null);
      response.setIsEntity(false);
      response.setPhoneNumber("+77077077007");
      response.setEmployeeCurator(null);
      return response;
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public UserResponseDTO signup(UserDataDTO createUserRequest) {
    if (!userRepository.existsByUsername(createUserRequest.getUsername())) {
      List<Role> roles = roleRepository.findAllByNameIn(createUserRequest.getRoles());
      User user = modelMapper.map(createUserRequest, User.class);
      user.setRoles(roles);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
    } else {
      throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public void delete(String username) {
    userRepository.deleteByUsername(username);
  }

  public User search(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return user;
  }

  public User whoami(HttpServletRequest req) {
    return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }

  public String refresh(String username) {
    return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
  }

}
