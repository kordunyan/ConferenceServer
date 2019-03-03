package com.aconference.controller;

import com.aconference.config.security.CurrentUser;
import com.aconference.config.security.oauth2.user.UserPrincipal;
import com.aconference.dto.response.PrincipalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/user-principal")
    public ResponseEntity<?> userPrinciple(@CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(PrincipalResponse.buildFromPrincipal(userPrincipal));
    }
}
