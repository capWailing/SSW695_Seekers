package com.seekers.seekerback.controller;

import com.seekers.seekerback.service.*;
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

    @GetMapping(value = "/restore")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public void restore(@RequestParam("id") String id,
                          @RequestParam("uuid") String uuid) {
        Repository.store(id, uuid);
    }

    @GetMapping(value = "/wordcloud", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getWordCloud(@RequestParam("id") String id,
                               @RequestParam("uuid") String uuid) throws IOException {
//        Repository.store(id, uuid);
        ByteArrayOutputStream outputStream = null;
        outputStream = WordCloudService.getGraph((id + uuid).toLowerCase());
        return outputStream.toByteArray();
    }

    @GetMapping(value = "/emojicloud", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getEmojiCloud(@RequestParam("id") String id,
                                @RequestParam("uuid") String uuid) throws IOException {
        ByteArrayOutputStream outputStream = null;
        outputStream = EmojiCloudService.getEmojiCloudGraph((id + uuid).toLowerCase());
        return outputStream.toByteArray();
    }

    @GetMapping(value = "/relationgraph", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getRelationGraph(@RequestParam("id") String id,
                                   @RequestParam("uuid") String uuid) throws IOException {
        ByteArrayOutputStream outputStream = null;
        outputStream = RelationGraphService.getGraph((id + uuid).toLowerCase());
        return outputStream.toByteArray();
    }

    @GetMapping(value = "/trendchart", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getTrendChart(@RequestParam("id") String id,
                                @RequestParam("uuid") String uuid) throws IOException {
        ByteArrayOutputStream outputStream = null;
        outputStream = TrendChartService.getTrendChart((id + uuid).toLowerCase());
        return outputStream.toByteArray();
    }
}
