package com.seekers.seekerback.controller;

import com.seekers.seekerback.entities.WordCloud;
import com.seekers.seekerback.service.Repository;
import com.seekers.seekerback.service.WordCloudService;
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
        boolean re = Repository.store(id);
        return WordCloudService.getGraph(id);
    }

    @PostMapping("/save")
    public String save(@RequestParam("id") String id){
        boolean re = Repository.store(id);
        return Boolean.toString(re);
    }
}
