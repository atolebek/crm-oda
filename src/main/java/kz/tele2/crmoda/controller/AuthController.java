package kz.tele2.crmoda.controller;

import javax.servlet.http.HttpServletRequest;

import kz.tele2.crmoda.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import kz.tele2.crmoda.dto.request.auth.UserDataDTO;
import kz.tele2.crmoda.dto.response.auth.UserResponseDTO;
import kz.tele2.crmoda.service.UserService;


@RestController
@RequestMapping("/auth")
@Api(tags = "Authorization")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;
  private final AuthService authService;
  private final ModelMapper modelMapper;

  @PostMapping("/local")
  @ApiOperation(value = "${AuthController.local}")
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 422, message = "Invalid username/password supplied")})
  public UserResponseDTO login(
      @ApiParam("Username") @RequestParam String username,
      @ApiParam("Password") @RequestParam String password) {
    return authService.localLogin(username, password);
  }

  @GetMapping("/refresh")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public String refresh(HttpServletRequest req) {
    return authService.refresh(req.getRemoteUser());
  }

}
