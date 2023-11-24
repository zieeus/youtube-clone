package com.jsproject.youtubeclone.controller;

import com.jsproject.youtubeclone.service.VideoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadVideo(@RequestParam("file")MultipartFile file) {
        try {
            videoService.uploadVideo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
