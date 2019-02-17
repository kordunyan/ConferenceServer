package com.oauthclient.controller;

import com.oauthclient.exception.ParseFileError;
import com.oauthclient.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping(value = "/api/upload")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/parse-emails")
    public ResponseEntity<?> parseEmails(@RequestParam("file") MultipartFile multipartFile) {
        try {
            return ResponseEntity.ok(fileService.parseEmails(multipartFile));
        } catch (ParseFileError parseFileError) {
            return ResponseEntity.badRequest().body(parseFileError.getMessage());
        }
    }


}
