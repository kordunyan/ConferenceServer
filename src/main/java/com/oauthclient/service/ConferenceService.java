package com.oauthclient.service;

import com.oauthclient.domain.Conference;
import com.oauthclient.repository.ConferenceRepository;
import org.springframework.stereotype.Service;

@Service
public class ConferenceService {

    private ConferenceRepository conferenceRepository;

    public ConferenceService(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    public Conference save(Conference conference) {
        return conferenceRepository.save(conference);
    }
}
