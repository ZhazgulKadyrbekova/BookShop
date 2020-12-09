package com.example.shop.controller;

import com.example.shop.dto.UserAdminDTO;
import com.example.shop.dto.UserAuthDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.service.UserService;
import com.example.shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/register")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public String createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PostMapping("/admin")
    public String createAdmin(@RequestBody UserAdminDTO userAdminDTO) {
        return userService.createAdmin(userAdminDTO);
    }

    @PostMapping("/saveAdmin")
    public String saveAdmin(@RequestBody UserDTO userDTO) {
        return userService.saveAdmin(userDTO);
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable("code") String code) {
        return userService.activateUser(code);
    }

    @PutMapping("/changePassword")
    public String changePassword(@RequestBody UserAuthDTO userAuthDTO) {
        return userService.changePassword(userAuthDTO.getEmail(), userAuthDTO.getPassword());
    }

    @PostMapping("/forgotPassword/{email}")
    public String sendForgottenPassword(@PathVariable("email") String email, Principal principal) {
        return userService.sendForgottenPassword(email);
    }

    @GetMapping("/hello")
    public String sayHello(Principal principal) {
        return "Hello, " + principal.getName();
    }

    @PostMapping("/auth")
    public String getToken(@RequestBody UserAuthDTO userAuthDTO) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAuthDTO.getEmail(), userAuthDTO.getPassword()));
        } catch (Exception e) {
            throw new Exception("Auth failed");
        }
        return jwtUtil.generateToken(userAuthDTO.getEmail());
    }
}
