package com.seekers.seekerback.controller;

import com.seekers.seekerback.service.Repository;
import com.seekers.seekerback.service.WordCloudService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * program: seeker-back
 * description: word cloud controller
 * author: Zituo Yan
 * create: 2020-10-28
 **/

@RestController
public class WordCloudController {

    @GetMapping(value = "/wordcloud", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getWordCloud(@RequestParam("id") String id) throws IOException {
        Repository.store(id);
        String filePath = WordCloudService.getGraph(id);
        FileInputStream inputStream = new FileInputStream(filePath);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }
}
