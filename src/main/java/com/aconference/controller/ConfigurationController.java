package com.aconference.controller;

import com.aconference.config.security.CurrentUser;
import com.aconference.config.security.oauth2.user.UserPrincipal;
import com.aconference.domain.Configuration;
import com.aconference.domain.User;
import com.aconference.dto.ConfigurationDto;
import com.aconference.service.ConfigurationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/config")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<?> getConfiguration(@CurrentUser UserPrincipal user) {
        return ResponseEntity.ok(mapper.map(user.getConfiguration(), ConfigurationDto.class));
    }

    @PostMapping
    public ResponseEntity<?> updateConfiguration(@RequestBody ConfigurationDto dto, @CurrentUser User user) {
        Configuration result = configurationService.updateConfiguration(dto, user.getConfiguration());
        return ResponseEntity.ok(mapper.map(result, ConfigurationDto.class));
    }

}
