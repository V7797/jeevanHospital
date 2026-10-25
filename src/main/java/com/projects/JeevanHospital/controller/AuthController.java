package com.projects.JeevanHospital.controller;

import com.projects.JeevanHospital.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // 1. क्रेडेंशियल्स व्हेरिफाय करा
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        
        // 2. युझर डिटेल्स लोड करा आणि टोकन जनरेट करा
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtHelper.generateToken(userDetails);

        // 3. टोकन रिस्पॉन्स म्हणून पाठवा
        return ResponseEntity.ok(Map.of("token", token));
    }
}
