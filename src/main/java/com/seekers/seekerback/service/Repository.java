package com.seekers.seekerback.service;

import com.alibaba.fastjson.JSON;
import com.seekers.seekerback.util.crawler.Crawler;
import com.seekers.seekerback.util.database.ISearchService;
import com.seekers.seekerback.util.database.impl.SearchServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * program: seeker-back
 * description: insert data in es
 * author: Zituo Yan
 * create: 2020-10-25
 **/
public class Repository {

    private static final Logger LOGGER = LoggerFactory.getLogger(Repository.class);

    private static ISearchService iSearchService = new SearchServiceImpl("localhost", 9200);


    private static boolean insertIntoES(String inputData){
        Map<String, Object> inputJSON = JSON.parseObject(inputData);
        List<Map<String, Object>> textList = (List<Map<String, Object>>) inputJSON.get("data");
        List<String> idList = new ArrayList<>();
        for (Map<String, Object> t: textList){
            idList.add((String) t.get("id"));
            t.remove("id");
        }
        LOGGER.info(idList.toString());
        LOGGER.info(textList.toString());
        String result = iSearchService.bulkUpsert("twitter", idList, textList);
        LOGGER.info(result);
        iSearchService.close();
        return "success".equals(result);
    }

    public static void store(String id){
        Crawler crawler = new Crawler(id);
        crawler.setMaxResults("100");
        crawler.setCreatedTime();
        try {
            boolean rs = insertIntoES(crawler.search());
            LOGGER.info("Bulk upsert " + rs);
        } catch (IOException | URISyntaxException e) {
            LOGGER.info(e.getMessage());
        }
    }

}