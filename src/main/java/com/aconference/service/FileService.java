package com.aconference.service;

import com.aconference.domain.Conference;
import com.aconference.domain.Configuration;
import com.aconference.domain.InvitationFile;
import com.aconference.domain.User;
import com.aconference.exception.ParseFileError;
import com.aconference.util.FileUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class FileService {

    private static Pattern EMAIL_PATTER = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    private static final String EMAIL_FILE_EXTENSION = "txt";

    public Set<String> parseEmails(MultipartFile file) throws ParseFileError {
        validateEmailFileExtension(file);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            Set<String> result = new HashSet<>();
            String emailLine;
            while((emailLine = br.readLine()) != null) {
                if (EMAIL_PATTER.matcher(emailLine).matches()) {
                    result.add(emailLine);
                }
            }
            return result;
        } catch (IOException e) {
            throw new ParseFileError(e.getMessage());
        }
    }

    public List<InvitationFile> saveConferenceFiles(List<MultipartFile> inviteFiles, File conferencePath) throws IOException {
        if (CollectionUtils.isEmpty(inviteFiles)) {
            return Collections.emptyList();
        }
        List<InvitationFile> result = new ArrayList<>();
        for (MultipartFile multipartFile : inviteFiles) {
            File dest = new File(FileUtils.concatPaths(conferencePath.getAbsolutePath(), multipartFile.getOriginalFilename()));
            multipartFile.transferTo(dest);
            result.add(new InvitationFile(multipartFile.getOriginalFilename(), dest.getAbsolutePath()));
        }
        return result;
    }

    public File getUserConferenceFilePath(Conference conference, User user) {
        Configuration configuration = user.getConfiguration();
        if (!FileUtils.pathExists(configuration.getInvitationFilesPath())) {
            throw new IllegalStateException(String.format("Path [%s] does not exist", configuration.getInvitationFilesPath()));
        }
        File userPath = FileUtils.createDirectory(FileUtils.concatPaths(configuration.getInvitationFilesPath(), buildUserFolderName(user)));
        return FileUtils.createDirectory(FileUtils.concatPaths(userPath.getAbsolutePath(), buildConferenceFoderName(conference)));

    }

    private String buildConferenceFoderName(Conference conference) {
        return FileUtils.clearDirectoryName(String.format("%s_%s", conference.getId(), conference.getTitle()));
    }

    private String buildUserFolderName(User user) {
        return FileUtils.clearDirectoryName(String.format("%s_%s", user.getId(), user.getEmail()));
    }

    private static void validateEmailFileExtension(MultipartFile multipartFile) throws ParseFileError {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if (!EMAIL_FILE_EXTENSION.equalsIgnoreCase(extension)) {
            throw new ParseFileError("Invalid extension for file");
        }
    }

}
