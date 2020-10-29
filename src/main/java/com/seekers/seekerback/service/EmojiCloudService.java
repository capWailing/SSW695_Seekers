package com.seekers.seekerback.service;


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
import java.util.Random;

public class EmojiCloudService {

    public static String getEmojiCloud() throws IOException {


        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        frequencyAnalyzer.setMinWordLength(4);
        //frequencyAnalyzer.setStopWords(loadStopWords());

        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load("C:/Users/wengq/OneDrive - stevens.edu/695/project/be695/SSW695_Seekers/src/main/java/com/seekers/seekerback/text/emoji.txt");
        final Dimension dimension = new Dimension(500, 312);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);

        wordCloud.setKumoFont(new KumoFont(new File("C:/Users/wengq/OneDrive - stevens.edu/695/project/be695/SSW695_Seekers/src/main/java/com/seekers/seekerback/font/OpenSansEmoji.ttf")));
        //wordCloud.setKumoFont(new KumoFont(EmojiCloud.class.getResourceAsStream("C:/Users/wengq/OneDrive - stevens.edu/695/project/be695/SSW695_Seekers/src/main/java/com/seekers/seekerback/font/symbol.ttf")));
        wordCloud.setPadding(2);
        //wordCloud.setBackground(new PixelBoundryBackground("backgrounds/whale_small.png"));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        wordCloud.build(buildWordFrequencies());
        wordCloud.writeToFile("emoji_cloud_small.png");

        return "url_emoji_cloud";
    }

    private static final Random RANDOM = new Random();

    private static List<WordFrequency> buildWordFrequencies() {
        final List<WordFrequency> wordFrequencies = new ArrayList<>();
        for (final String emoji : EMOJIS) {
            wordFrequencies.add(new WordFrequency(emoji, RANDOM.nextInt(250)));
        }
        return wordFrequencies;
    }

    private static final String[] EMOJIS = {
            "emoji",
            "kumo",
            "\uD83D\uDE02",
            "\uD83D\uDC4D",
            "\uD83D\uDE0A",
            "\uD83D\uDE01",
            "\uD83D\uDE12",
            "\uD83D\uDE11",
            "\uD83D\uDD25",
            "\uD83D\uDE33",
            "\uD83D\uDCAF",
            "\uD83D\uDE32",
            "\uD83D\uDE31",
            "\uD83D\uDE0F",
            "\uD83D\uDC44",
            "\uD83D\uDC40 "
    };

    public static void main(String[] args) throws IOException{
        getEmojiCloud();
    }
}
