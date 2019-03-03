package com.aconference.service;

import com.aconference.domain.Conference;
import com.aconference.domain.EmailTo;
import com.aconference.domain.InvitationFile;
import com.aconference.domain.User;
import com.aconference.dto.request.conference.SendConferenceEmailRequest;
import com.aconference.exception.SendConferenceEmailRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConferenceManagerService {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private FileService fileService;

    @Transactional
    public Conference buildConferenceAndSendEmail(SendConferenceEmailRequest sendConferenceEmailRequest, User user) throws SendConferenceEmailRequestException, IOException {
        Optional<Conference> optionalConference = conferenceService.findByIdAndUser(sendConferenceEmailRequest.getId(), user);
        if (!optionalConference.isPresent()) {
            throw new SendConferenceEmailRequestException(String.format("Conference with id [%s] does not exist",
                    sendConferenceEmailRequest.getId()));
        }
        Conference conference = optionalConference.get();

        conference.setSubject(sendConferenceEmailRequest.getSubject());
        conference.setEmailContent(sendConferenceEmailRequest.getEmailContent());
        fillEmailsTo(conference, getEmailsTo(sendConferenceEmailRequest));

        File conferencePath = fileService.getUserConferenceFilePath(conference, user);
        List<InvitationFile> invitationFiles = fileService.saveConferenceFiles(sendConferenceEmailRequest.getInviteFiles(), conferencePath);
        conference.addAllInvitationFiles(invitationFiles);
        conferenceService.save(conference);
        return conference;
    }


    private void fillEmailsTo(Conference conference, List<EmailTo> emailTos) {
        emailTos.stream()
                .filter(conference::hasNotEmailTo)
                .forEach(conference::addEmailTo);
    }

    private List<EmailTo> getEmailsTo(SendConferenceEmailRequest sendConferenceEmailRequest) {
        return sendConferenceEmailRequest.getEmailsTo()
                .stream()
                .map(EmailTo::new)
                .collect(Collectors.toList());
    }
}
