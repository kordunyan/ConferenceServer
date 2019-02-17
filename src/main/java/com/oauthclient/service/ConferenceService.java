package com.oauthclient.service;

import com.oauthclient.domain.Conference;
import com.oauthclient.domain.User;
import com.oauthclient.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConferenceService {

    private ConferenceRepository conferenceRepository;

    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    public Conference save(Conference conference) {
        return conferenceRepository.save(conference);
    }

    public boolean existsBySubject(String subject, User user) {
        return conferenceRepository.existsBySubjectAndUser(subject, user);
    }

    public List<Conference> findAllByUser(User user) {
        return conferenceRepository.findAllByUserOrderByIdDesc(user);
    }

    public Optional<Conference> findByIdAndUser(Long id, User user) {
        return conferenceRepository.findByIdAndUser(id, user);
    }
}
