package com.aconference.controller;

import com.aconference.dto.request.conference.PathExistsRequest;
import com.aconference.exception.ParseFileError;
import com.aconference.service.FileService;
import com.aconference.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/upload")
public class FileController {

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

    @PostMapping("/pat-exists")
    public ResponseEntity<?> pathExists(@RequestBody PathExistsRequest pathExistsRequest) {
        return ResponseEntity.ok(FileUtils.pathExists(pathExistsRequest.getPath()));
    }

}
