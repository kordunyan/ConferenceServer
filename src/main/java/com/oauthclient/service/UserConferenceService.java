package com.oauthclient.service;

import com.oauthclient.config.security.oauth2.user.UserPrincipal;
import com.oauthclient.domain.Conference;
import com.oauthclient.domain.User;
import com.oauthclient.dto.request.conference.NewConferenceRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserConferenceService {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public Conference saveNewConference(NewConferenceRequest conferenceRequest, UserPrincipal userPrincipal) {
        Optional<User> userOptional = userService.findById(userPrincipal.getId());
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(String.format("User with id: %s not found",
                userPrincipal.getId())));
        Conference conference = mapper.map(conferenceRequest, Conference.class);
        conference.setDateCreated(LocalDate.now());
        conference.setUser(user);
        return conferenceService.save(conference);
    }
}
