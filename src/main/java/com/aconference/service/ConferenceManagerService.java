package com.aconference.service;

import com.aconference.domain.Conference;
import com.aconference.domain.EmailTo;
import com.aconference.domain.InvitationFile;
import com.aconference.domain.User;
import com.aconference.dto.request.conference.SendConferenceEmailRequest;
import com.aconference.exception.SendConferenceEmailRequestException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConferenceManagerService {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FileService fileService;

    @Transactional
    public Conference buildConferenceAndSendEmail(SendConferenceEmailRequest sendConferenceEmailRequest, User user) throws SendConferenceEmailRequestException, IOException, MessagingException, GeneralSecurityException {
        Optional<Conference> optionalConference = conferenceService.findByIdAndUser(sendConferenceEmailRequest.getId(), user);
        if (!optionalConference.isPresent()) {
            throw new SendConferenceEmailRequestException(String.format("Conference with id [%s] does not exist",
                    sendConferenceEmailRequest.getId()));
        }
        Conference conference = optionalConference.get();

        conference.setSubject(sendConferenceEmailRequest.getSubject());
        conference.setEmailContent(sendConferenceEmailRequest.getEmailContent());
        mergeEmailsTo(conference, sendConferenceEmailRequest.getEmailsTo());
        File conferencePath = fileService.getUserConferenceFilePath(conference, user);
        List<InvitationFile> newInvitationFiles = fileService.saveConferenceFiles(sendConferenceEmailRequest.getInviteFiles(), conferencePath);
        mergeInvitationFiles(conference, newInvitationFiles, sendConferenceEmailRequest.getSavedInvitationFilesIds());
        //emailService.sendInvitationEmails(conference, user);
        conference.setEmailSent(true);
        return conference;
    }

    private void mergeInvitationFiles(Conference conference, List<InvitationFile> newInvitationFiles, Set<Long> remainedIds) {
        if (CollectionUtils.isEmpty(conference.getInvitationFiles())) {
            conference.setAllInvitationFiles(newInvitationFiles);
        }
        Iterator<InvitationFile> invitationFilesIterator = conference.getInvitationFiles().iterator();
        while (invitationFilesIterator.hasNext()) {
            InvitationFile invitationFile = invitationFilesIterator.next();
            if (remainedIds != null && !remainedIds.contains(invitationFile.getId())) {
                invitationFilesIterator.remove();
            }
            if (CollectionUtils.isNotEmpty(newInvitationFiles)) {
                newInvitationFiles.stream()
                        .filter(newInvitationFile -> newInvitationFile.getSystemFileName().equals(invitationFile.getSystemFileName()))
                        .findFirst()
                        .ifPresent( newInvitationFile -> invitationFilesIterator.remove());
            }
        }
        conference.addAllInvitationFiles(newInvitationFiles);
    }


    private void mergeEmailsTo(Conference conference, Set<String> newEmailsTo) {
        if (CollectionUtils.isEmpty(conference.getEmailsTo())) {
            conference.setAllEmailsTo(convertEmailsTo(newEmailsTo));
            return;
        }
        Iterator<EmailTo> emailsToIterator = conference.getEmailsTo().iterator();

        while (emailsToIterator.hasNext()) {
            EmailTo emailTo = emailsToIterator.next();
            if (newEmailsTo.contains(emailTo.getEmail())) {
                newEmailsTo.remove(emailTo.getEmail());
            } else {
                emailsToIterator.remove();
            }
        }
        if (!newEmailsTo.isEmpty()) {
            conference.addAllEmailsTo(convertEmailsTo(newEmailsTo));
        }
    }

    private List<EmailTo> convertEmailsTo(Collection<String> emails) {
        return emails.stream()
                .map(EmailTo::new)
                .collect(Collectors.toList());
    }
}
