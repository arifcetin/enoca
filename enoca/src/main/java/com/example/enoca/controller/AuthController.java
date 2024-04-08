package com.example.enoca.controller;

import com.example.enoca.Dto.AuthDto;
import com.example.enoca.entity.Customer;
import com.example.enoca.security.JwtTokenProvider;
import com.example.enoca.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, CustomerService customerService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody Customer loginCustomer){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginCustomer.getName(),loginCustomer.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        Customer customer = customerService.getOneCustomerByName(loginCustomer.getName());
        AuthDto authDto = new AuthDto();
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        authDto.setMessage("Başarıyla giriş yapıldı.");
        authDto.setAccessToken(jwtToken);
        authDto.setCustomerId(customer.getCustomerId());
        return new ResponseEntity<>(authDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDto> register(@RequestBody Customer registerCustomer){
        AuthDto authDto = new AuthDto();
        if (customerService.getOneCustomerByName(registerCustomer.getName()) != null){
            authDto.setMessage("Kullanıcı adı zaten kullanılıyor");
            return new ResponseEntity<>(authDto, HttpStatus.BAD_REQUEST);
        }
        Customer newCustomer = new Customer();
        newCustomer.setName(registerCustomer.getName());
        newCustomer.setPassword(passwordEncoder.encode(registerCustomer.getPassword()));
        customerService.addCustomer(newCustomer);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                registerCustomer.getName(),registerCustomer.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        authDto.setMessage("Kullanıcı başarıyla oluşturuldu.");
        authDto.setAccessToken("Bearer "+ jwtToken);
        authDto.setCustomerId(newCustomer.getCustomerId());
        return new ResponseEntity<>(authDto, HttpStatus.CREATED);
    }

}
