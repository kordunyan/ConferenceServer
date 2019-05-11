package com.aconference.service.mail;


import com.aconference.domain.User;
import com.aconference.service.UserService;
import com.aconference.service.google.GmailService;
import com.aconference.service.google.entity.GoogleException;
import com.aconference.service.google.entity.message.LightMail;
import com.aconference.service.google.entity.message.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserMailProcessor {

    @Autowired
    private UserService userService;

    @Autowired
    private GmailService gmailService;


    public void processUsersMails() throws GoogleException {


        List<User> usersWithSentConference = userService.getUsersWithSentConference();


    }


}
