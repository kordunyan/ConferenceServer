package com.aconference.service;

import com.aconference.domain.EmailTo;
import com.aconference.repository.EmailToRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailToService {

    private EmailToRepository emailToRepository;

    public EmailToService(EmailToRepository emailToRepository) {
        this.emailToRepository = emailToRepository;
    }

    public void saveAll(List<EmailTo> emailsTo) {
        emailToRepository.saveAll(emailsTo);
    }
}
