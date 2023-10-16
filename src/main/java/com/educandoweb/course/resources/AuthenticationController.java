package com.educandoweb.course.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.entities.AuthenticationDTO;
import com.educandoweb.course.entities.RegisterDTO;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.entities.enums.UserRoles;
import com.educandoweb.course.infra.security.TokenService;
import com.educandoweb.course.repositories.UserRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository repository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        // Validando o login
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        // Gerando o JWT
        var token = tokenService.generateToken((User) auth.getPrincipal());
        // Retornando o JWT
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid User data) {
        if (this.repository.findByEmailIgnoreCase(data.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Esse email já está sendo usado");
        } else {
            // Encriptar a senha
            // Vai ser criado User comum!!!!!!!!!
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
            User newUser = new User(data.getId(), data.getName(), data.getEmail(), data.getPhone(), encryptedPassword,
                    UserRoles.USER);
            this.repository.save(newUser);

            // TODO: Mudar para ficar igual no curso do Nelio Alves, com o CREATED e a URI.
            return ResponseEntity.ok().build();

        }
    }
}
