package com.seekers.seekerback.controller;

import com.seekers.seekerback.service.EmojiCloudService;
import com.seekers.seekerback.service.Repository;
import com.seekers.seekerback.service.WordCloudService;
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
    @PostMapping(value = "/send")
    public void getId(@RequestParam("id") String id){
        Repository.store(id);
    }

    @GetMapping(value = "/wordcloud", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getWordCloud() throws IOException {
        String filePath = WordCloudService.getGraph();
        FileInputStream inputStream = new FileInputStream(filePath);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        inputStream.close();
        return bytes;
    }

    @GetMapping(value = "/emojicloud", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getEmojiCloud() throws IOException {
        ByteArrayOutputStream outputStream = null;
        outputStream = EmojiCloudService.getEmojiCloudGraph();
        return outputStream.toByteArray();
    }

//    @DeleteMapping(value = "/delete")
//    public void deleteData(){
//
//    }
}
