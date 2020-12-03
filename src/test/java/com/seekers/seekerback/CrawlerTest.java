package com.seekers.seekerback;

import com.seekers.seekerback.util.crawler.Crawler;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @Author Danping Cai 10456033
 * @Description CS546
 * @Date 11/15/20
 **/
public class CrawlerTest {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Crawler crawler = new Crawler("WayVSubs2019");
        crawler.setMaxResults("100");
        String re = crawler.search();
        System.out.println();
    }
}
