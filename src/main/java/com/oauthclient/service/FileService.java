package com.oauthclient.service;

import com.oauthclient.exception.ParseFileError;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
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

    private static void validateEmailFileExtension(MultipartFile multipartFile) throws ParseFileError {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if (!EMAIL_FILE_EXTENSION.equalsIgnoreCase(extension)) {
            throw new ParseFileError("Invalid extension for file");
        }
    }

}
