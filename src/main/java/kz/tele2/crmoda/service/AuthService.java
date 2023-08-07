package kz.tele2.crmoda.service;

import kz.tele2.crmoda.dto.request.auth.UserDataDTO;
import kz.tele2.crmoda.dto.response.auth.UserResponseDTO;
import kz.tele2.crmoda.exception.CustomException;
import kz.tele2.crmoda.model.Role;
import kz.tele2.crmoda.model.User;
import kz.tele2.crmoda.repository.RoleRepository;
import kz.tele2.crmoda.repository.UserRepository;
import kz.tele2.crmoda.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService {

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
            if (user.getIsDeleted()) {
                SecurityContextHolder.clearContext();
                throw new CustomException("This user is deleted. Please contact administrator", HttpStatus.FORBIDDEN);
            }
            String jwtToken = jwtTokenProvider.createToken(username, user.getRoles());
            UserResponseDTO response = modelMapper.map(user, UserResponseDTO.class);
            response.setJwt(jwtToken);
            return response;
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
    }

}
