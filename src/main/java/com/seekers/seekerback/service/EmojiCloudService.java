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
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.*;
import com.seekers.seekerback.util.database.ISearchService;
import com.seekers.seekerback.util.database.impl.SearchServiceImpl;

public class EmojiCloudService {

    public static List<WordFrequency> getEmojiFrequency (String db_id) throws UnsupportedEncodingException {


        List<Map<String, Object>> text1_json = new ArrayList<>();

//        List<String> text3 = new ArrayList<>();

        ISearchService iSearchService = new SearchServiceImpl("localhost", 9200);
        List<String> result = iSearchService.idQuery(db_id);
        for (String e : result) {
//            System.out.println(e);
            System.out.println(iSearchService.get(db_id, e));
            text1_json.add(iSearchService.get(db_id, e));
        }
        System.out.println("***********");
        System.out.println(result);


        iSearchService.close();
        //System.out.println(text1_json);

        String emojiRe = "[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]";
        Pattern r = Pattern.compile(emojiRe);
        Matcher m;

        //String newStr;

        //String onlyEmoji = " ";



        Map<String, Integer> map = new HashMap<String, Integer>();
        int temp = 0;

        for (Map<String, Object> e : text1_json) {
//            text3.add(e.get("text").toString()
//                    .replaceAll("\\w*", "")
//                    //.replaceAll("\\\\u[A-Z0-9]{4}\\\\u[A-Z0-9]{4}", "")
//                    .replaceAll("[!\\\"#$%&'()*+,-./:;<=>?@\\\\\\\\\\\\[\\\\]^_`{|}~]*", "")
//                    .replaceAll("[\\\\ud800\\\\udc00-\\\\udbff\\\\udfff\\\\ud800-\\\\udfff]", "")



            //newStr = new String(e.get("text").toString().getBytes(), "UTF-8");
            m = r.matcher(e.get("text").toString());
            //System.out.println(e.get("text").toString());
//            System.out.println(m.find());


            while(m.find()) {
                //System.out.println("**********************");
                //onlyEmoji = onlyEmoji + " " + m.group() + "l";

                if (map.get(m.group()) == null) {
                    map.put(m.group(), 1);
                } else {
                    temp = map.get(m.group())+1;
                    map.put(m.group(), temp);

                }
            }

            //text3.add(onlyEmoji);

        }


//        System.out.println(text3);
//        return text3;




        final List<WordFrequency> wordFrequencies = new ArrayList<>();


        for (Map.Entry<String, Integer> entry: map.entrySet()) {

            String key = entry.getKey();

            Integer value = entry.getValue();
            wordFrequencies.add(new WordFrequency(key, value));

        }


        System.out.println(wordFrequencies);
        return wordFrequencies;




    }


    public static ByteArrayOutputStream getEmojiCloudGraph(String db_id) throws IOException {

        //analyzer settings

        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        //frequencyAnalyzer.setMinWordLength(4);
        //frequencyAnalyzer.setStopWords(loadStopWords());

        //load frequency using getEmojiFrequency, need to give id to it

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.loadWordFrequencies(getEmojiFrequency(db_id));
                //loadWordFrequencies(getEmojiFrequency(id));

        //new wordcloud object




        final Dimension dimension = new Dimension(250, 250);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);

        //set right font for emoji display ****IMPORTANT*****
        wordCloud.setKumoFont(new KumoFont(new File("font/OpenSansEmoji.ttf")));
        //wordCloud.setKumoFont(new KumoFont(EmojiCloud.class.getResourceAsStream("font/OpenSansEmoji.ttf")));

        //format settings. can be modified later.

        wordCloud.setPadding(2);
        wordCloud.setBackgroundColor(new Color(0xffffff));
        //wordCloud.setBackground(new PixelBoundryBackground("backgrounds/whale_small.png"));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new LinearFontScalar(10, 100));

        //build wordcloud with wordFrequencies at line 65
        wordCloud.build(wordFrequencies);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wordCloud.writeToStreamAsPNG(outputStream);
//        wordCloud.writeToFile("emoji_cloud_small.png");

        return outputStream;
    }

}
