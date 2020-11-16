package com.seekers.seekerback.controller;

import com.seekers.seekerback.service.EmojiCloudService;
import com.seekers.seekerback.service.Repository;
import com.seekers.seekerback.service.WordCloudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        ByteArrayOutputStream outputStream = null;
        outputStream = WordCloudService.getGraph();
        return outputStream.toByteArray();
    }

    @GetMapping(value = "/emojicloud", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getEmojiCloud() throws IOException {
        ByteArrayOutputStream outputStream = null;
        outputStream = EmojiCloudService.getEmojiCloudGraph("twitter");
        return outputStream.toByteArray();
    }

//    @DeleteMapping(value = "/delete")
//    public void deleteData(){
//
//    }
}
