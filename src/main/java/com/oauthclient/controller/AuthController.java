package com.oauthclient.controller;

import com.oauthclient.config.security.CurrentUser;
import com.oauthclient.config.security.oauth2.user.UserPrincipal;
import com.oauthclient.dto.response.PrincipalResponse;
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
