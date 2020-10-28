package com.seekers.seekerback.controller;

import com.seekers.seekerback.entities.WordCloud;
import com.seekers.seekerback.service.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * program: seeker-back
 * description: word cloud controller
 * author: Zituo Yan
 * create: 2020-10-28
 **/

@RestController
public class WordCloudController {

    @PostMapping("/wordcloud")
    public String getWordCloud(@RequestParam("id") String id){
        Repository.store(id);
        WordCloud result = new WordCloud(id);
        return result.getUrl();
    }
}
