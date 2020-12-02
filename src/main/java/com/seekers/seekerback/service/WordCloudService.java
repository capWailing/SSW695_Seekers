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
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WordCloudService {
    public static ByteArrayOutputStream getGraph(String databaseid) {
        List<Map<String, Object>> text1_json = new ArrayList<>();
        List<String> text3 = new ArrayList<>();

        ISearchService iSearchService = new SearchServiceImpl("localhost", 9200);
        List<String> result = iSearchService.idQuery(databaseid);
        for (String e : result) {
            text1_json.add(iSearchService.get(databaseid, e));
        }
        for (Map<String, Object> e : text1_json) {
            System.out.println(e.get("text"));
            text3.add(e.get("text").toString()
                    //remove something here
                    //url
                    .replaceAll("http\\S+", "")
                    //time
                    .replaceAll("\\d:\\d\\dpmE", ""));
        }
        List<String> stopword = new ArrayList<>();
        stopword.add("the");

        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setStopWords(stopword);

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(text3);
        final Dimension dimension = new Dimension(600, 600);

        com.kennycason.kumo.WordCloud wordCloud = new com.kennycason.kumo.WordCloud(dimension, CollisionMode.PIXEL_PERFECT);

        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setBackgroundColor(new Color(0xffffff));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new SqrtFontScalar(10,100));
        wordCloud.build(wordFrequencies);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wordCloud.writeToStreamAsPNG(outputStream);

        //response for service
        return outputStream;
    }
}
