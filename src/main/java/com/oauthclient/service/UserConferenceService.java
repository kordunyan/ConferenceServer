package com.oauthclient.service;

import com.oauthclient.config.security.oauth2.user.UserPrincipal;
import com.oauthclient.domain.Conference;
import com.oauthclient.dto.request.conference.NewConferenceRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserConferenceService {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    public Conference saveNewConference(NewConferenceRequest conferenceRequest, UserPrincipal userPrincipal) {
        Conference conference = mapper.map(conferenceRequest, Conference.class);
        conference.setDateCreated(LocalDate.now());
        conference.setUser(userPrincipal);
        return conferenceService.save(conference);
    }

    public List<Conference> findAllByUser(UserPrincipal user) {
        return conferenceService.findAllByUser(user);
    }

    public boolean existsBySubject(String subject, UserPrincipal user) {
        return conferenceService.existsBySubject(subject, user);
    }

    public Optional<Conference> findByIdAndUser(Long id, UserPrincipal user) {
        return conferenceService.findByIdAndUser(id, user);
    }
}
