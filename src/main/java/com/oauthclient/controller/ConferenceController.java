package com.oauthclient.controller;

import com.oauthclient.config.security.CurrentUser;
import com.oauthclient.config.security.oauth2.user.UserPrincipal;
import com.oauthclient.domain.Conference;
import com.oauthclient.dto.request.conference.NewConferenceRequest;
import com.oauthclient.dto.response.ConferenceResponse;
import com.oauthclient.service.UserConferenceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/conference")
public class ConferenceController {

    @Autowired
    private UserConferenceService userConferenceService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<?> createNewConference(@Valid @RequestBody NewConferenceRequest conferenceRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        Conference conference = userConferenceService.saveNewConference(conferenceRequest, userPrincipal);
        return ResponseEntity.ok(mapper.map(conference, ConferenceResponse.class));
    }

}
