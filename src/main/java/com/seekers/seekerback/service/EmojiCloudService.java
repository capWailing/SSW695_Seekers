package com.seekers.seekerback.service;

import com.seekers.seekerback.util.database.ISearchService;
import com.seekers.seekerback.util.database.impl.SearchServiceImpl;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import com.kennycason.kumo.palette.LinearGradientColorPalette;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.alibaba.fastjson.*;
import com.seekers.seekerback.util.database.ISearchService;
import com.seekers.seekerback.util.database.impl.SearchServiceImpl;

public class EmojiCloudService {

    public static List<WordFrequency> getEmojiFrequency (int id) {


        List<Map<String, Object>> text1_json = new ArrayList<>();

        List<String> text3 = new ArrayList<>();

        ISearchService iSearchService = new SearchServiceImpl("localhost", 9200);
        List<String> result = iSearchService.idQuery("twitter");
        for (String e : result) {
            //System.out.println(e);
            //System.out.println(iSearchService.get("twitter", e));
            text1_json.add(iSearchService.get("twitter", e));
        }
        System.out.println(text1_json);

        for (Map<String, Object> e : text1_json) {
            System.out.println(e.get("text"));
            text3.add(e.get("text").toString()
                    //remove something here
                    //url
                    .replaceAll("http\\S+", "")
                    //time
                    .replaceAll("\\d:\\d\\dpmE", ""));
            //stop word dictionary should be here

        }
//        for (String e:text){
//            text2= text2.concat(e);
//            text2= text2.concat(" ");
//        }
//
//        System.out.println(text2);
//        text3.add(text2);

//        List<String> text = new ArrayList();
//        text.add("\uD83D\uDE32sdfsadf");
//        text.add("\uD83D\uDC44fsdf");
//        text.add("kumokumo");
//
//        ISearchService iSearchService = new SearchServiceImpl("localhost", 9200);
//        for (int i = 0; i < 10; i++){
//            iSearchService.getByJson(id);






//get data from database as json, parse it into a emoji word frequency
//not finished, for testing

        final List<WordFrequency> wordFrequencies = new ArrayList<>();
        for (final String emoji : EMOJIS) {
            wordFrequencies.add(new WordFrequency(emoji, RANDOM.nextInt(250)));
        }
        return wordFrequencies;

    }


    public static String getEmojiCloudGraph(int id) throws IOException {

        //analyzer settings

        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        //frequencyAnalyzer.setMinWordLength(4);
        //frequencyAnalyzer.setStopWords(loadStopWords());

        //load frequency using getEmojiFrequency, need to give id to it

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.loadWordFrequencies(getEmojiFrequency(id));

        //new wordcloud object

        final Dimension dimension = new Dimension(250, 250);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);

        //set right font for emoji display ****IMPORTANT*****
        wordCloud.setKumoFont(new KumoFont(new File("font/OpenSansEmoji.ttf")));
        //wordCloud.setKumoFont(new KumoFont(EmojiCloud.class.getResourceAsStream("font/OpenSansEmoji.ttf")));

        //format settings. can be modified later.

        wordCloud.setPadding(2);
        //wordCloud.setBackground(new PixelBoundryBackground("backgrounds/whale_small.png"));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));

        //build wordcloud with wordFrequencies at line 65
        wordCloud.build(wordFrequencies);
        wordCloud.writeToFile("emoji_cloud_small.png");

        return "url_emoji_cloud";
    }



    //for testing
    private static final Random RANDOM = new Random();

    private static final String[] EMOJIS = {
            "TEST6858678",
            "\uD83D\uDC4D",
            "\uD83D\uDE0A",
            "\uD83D\uDE01",
            "\uD83D\uDE12",
            "\uD83D\uDE11",
            "\uD83D\uDE0D",
            "\uD83D\uDC4C",
            "\uD83D\uDC55",
            "\uD83D\uDCAA",
            "\uD83D\uDE4C",
            "\uD83D\uDE2D",
            "\uD83D\uDE29",
            "\uD83D\uDE21",
            "\uD83D\uDD25",
            "\uD83D\uDE33",
            "\uD83D\uDD25",
            "\uD83D\uDE21",
            "\uD83D\uDCA9",
            "\uD83D\uDC7B",
            "\uD83D\uDC7D",
            "\uD83D\uDC57",
            "\uD83D\uDD25",
            "\uD83C\uDF53",
            "\uD83C\uDFB1",
            "\uD83C\uDFA7",
            "\uD83D\uDE91",
            "\uD83C\uDFEB",
            "\uD83D\uDCBE",
            "\uD83D\uDCAF",
            "\uD83D\uDE32",
            "\uD83D\uDE31",
            "\uD83D\uDE0F",
            "\uD83D\uDC44",
            "\uD83D\uDC40"
    };


    public static void main(String[] args) throws IOException{
        getEmojiFrequency(0);
    }


}
