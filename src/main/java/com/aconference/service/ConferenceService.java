package com.aconference.service;

import com.aconference.domain.Conference;
import com.aconference.domain.User;
import com.aconference.repository.ConferenceRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ConferenceService {

    private InvitationFileService invitationFileService;
    private ConferenceRepository conferenceRepository;
    private EmailToService emailToService;

    public ConferenceService(InvitationFileService invitationFileService, ConferenceRepository conferenceRepository,
            EmailToService emailToService) {
        this.invitationFileService = invitationFileService;
        this.conferenceRepository = conferenceRepository;
        this.emailToService = emailToService;
    }

    @Transactional
    public Conference save(Conference conference) {
        if (CollectionUtils.isNotEmpty(conference.getEmailsTo())) {
            emailToService.saveAll(conference.getEmailsTo());
        }
        if (CollectionUtils.isNotEmpty(conference.getInvitationFiles())) {
            invitationFileService.saveAll(conference.getInvitationFiles());
        }
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
