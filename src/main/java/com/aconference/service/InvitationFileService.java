package com.aconference.service;

import com.aconference.domain.InvitationFile;
import com.aconference.repository.InvitationFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationFileService {

    private InvitationFileRepository invitationFileRepository;

    public InvitationFileService(InvitationFileRepository invitationFileRepository) {
        this.invitationFileRepository = invitationFileRepository;
    }

    public void saveAll(List<InvitationFile> files) {
        invitationFileRepository.saveAll(files);
    }
}
