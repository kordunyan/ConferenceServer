package com.aconference.controller;

import com.aconference.config.security.CurrentUser;
import com.aconference.config.security.oauth2.user.UserPrincipal;
import com.aconference.domain.Conference;
import com.aconference.domain.User;
import com.aconference.dto.request.conference.NewConferenceRequest;
import com.aconference.dto.request.conference.SendConferenceEmailRequest;
import com.aconference.dto.response.ConferenceResponse;
import com.aconference.exception.SendConferenceEmailRequestException;
import com.aconference.service.ConferenceManagerService;
import com.aconference.service.UserConferenceService;
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
    private ConferenceManagerService conferenceManagerService;

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

    @PostMapping("/send-email")
    public ResponseEntity<?> sendConferenceEmail(@Valid SendConferenceEmailRequest sendConferenceEmailRequest, @CurrentUser User user) {
        try {
            conferenceManagerService.buildConferenceAndSendEmail(sendConferenceEmailRequest, user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("OK");
    }

}
