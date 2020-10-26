package com.seekers.seekerback.service;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import com.seekers.seekerback.util.database.ISearchService;
import com.seekers.seekerback.util.database.impl.SearchServiceImpl;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordCloud {
    public static void main(String[] args) throws IOException {
        //Fetch data from database
        List<String> text = new ArrayList<String>();
        List<String> text1 = new ArrayList<String>();
        List<String> text3 = new ArrayList<String>();
        String text2 = "";

        ISearchService iSearchService = new SearchServiceImpl("localhost", 9200);
        List<String> result = iSearchService.idQuery("twitter");
        for (String e: result) {
            System.out.println(iSearchService.get("twitter", e));

            text1.add(iSearchService.get("twitter", e).toString());
        }
        System.out.println(text1);
        for (String e: text1) {
            if(e.length()>31) {
                System.out.println(e.substring(30, e.length() - 1));
                text.add(e.substring(30, e.length() - 1)
                        //remove something here
                        //url
                        .replaceAll("http\\S+", "")
                        //time
                        .replaceAll("\\d:\\d\\dpmE", ""));
            }

        }
        for (String e:text){
            text2= text2.concat(e);
            text2= text2.concat(" ");
        }

        System.out.println(text2);
        text3.add(text2);
        //Kumo API start from here, include frequency analyzer and image generate.
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(text3);
        final Dimension dimension = new Dimension(600, 600);
        com.kennycason.kumo.WordCloud wordCloud = new com.kennycason.kumo.WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("./datarank_wordcloud_circle_sqrt_font.png");
    }
}
