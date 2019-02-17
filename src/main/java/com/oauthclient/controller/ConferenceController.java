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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/conference")
public class ConferenceController {

    @Autowired
    private UserConferenceService userConferenceService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping()
    public ResponseEntity<?> createNewConference(@Valid @RequestBody NewConferenceRequest conferenceRequest,
            @CurrentUser UserPrincipal user) {
        Conference conference = userConferenceService.saveNewConference(conferenceRequest, user);
        return ResponseEntity.ok(mapper.map(conference, ConferenceResponse.class));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@CurrentUser UserPrincipal user) {
        List<ConferenceResponse> result = userConferenceService.findAllByUser(user)
                .stream()
                .map(conference -> mapper.map(conference, ConferenceResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/exists")
    public ResponseEntity<?> existsBySubkect(@RequestParam("subject") String subject, @CurrentUser UserPrincipal user) {
        return ResponseEntity.ok(userConferenceService.existsBySubject(subject, user));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id, @CurrentUser UserPrincipal user) {
        Optional<Conference> optionalConference = userConferenceService.findByIdAndUser(id, user);
        if (optionalConference.isPresent()) {
            return ResponseEntity.ok(optionalConference.map(conference -> mapper.map(conference, ConferenceResponse.class)));
        }
        return ResponseEntity.notFound().build();
    }

}
