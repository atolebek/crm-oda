package kz.tele2.crmoda;

import java.util.ArrayList;
import java.util.Arrays;

import kz.tele2.crmoda.dto.response.auth.UserResponseDTO;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.model.Role;
import kz.tele2.crmoda.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class CrmOdaApp implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(CrmOdaApp.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Override
  public void run(String... params) throws Exception {
//    User admin = new User();
//    admin.setUsername("admin");
//    admin.setPassword("admin");
//    admin.setEmail("admin@email.com");
//    admin.setTermsOfUse(true);
//    admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_CLIENT)));
//
//    try {
//      UserResponseDTO responseDTO = userService.signup(admin);
//    } catch (Exception e) {
//
//    }
//
//    User client = new User();
//    client.setUsername("client");
//    client.setPassword("client");
//    client.setEmail("client@email.com");
//    client.setTermsOfUse(false);
//    client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
//
//    try {
//      UserResponseDTO responseDTO = userService.signup(client);
//    } catch (Exception e){
//
//    }
  }

}
