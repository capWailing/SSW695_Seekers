package com.seekers.seekerback.util.crawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * @Author Danping Cai 10456033
 * @Description SSW695
 * @Date 10/19/20
 **/
public class Crawler {
    String bearerToken;
    String searchString;
    ArrayList<NameValuePair> queryParameters = new ArrayList<>();

    public Crawler(String userName) {
        bearerToken = "AAAAAAAAAAAAAAAAAAAAAN5wIAEAAAAA4O0tnpXXWxE5ioOJxNpqC96gi40%3Dtq4XCXCp9FR93158A6amoT8KCjvIsTrcezo7HSfPprwB8Okziv";
        queryParameters.add(new BasicNameValuePair("query", "from:" + userName));
    }

    private void constructor(String userName, String startTime, String endTime) {
        searchString = "from:" + userName + "&start_time:" + startTime + "&end_time:" + endTime;
    }

    public void setMaxResults(String maxResults) {
        queryParameters.add(new BasicNameValuePair("max_results", maxResults));
    }

    public void setCreatedTime() {
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
    }

    private void setStartedTime(String startTime) {
        queryParameters.add(new BasicNameValuePair("start_time", startTime));
    }

    private void removeStartedTime() {
        queryParameters.removeIf(item -> item.getName().equals("start_time"));
    }

    private void setEndTime(String endTime) {
        queryParameters.add(new BasicNameValuePair("end_time", endTime));
    }

    private void removeEndTime() {
        queryParameters.removeIf(item -> item.getName().equals("end_time"));
    }

    private void setSinceId(String sinceId) {
        queryParameters.add(new BasicNameValuePair("since_id", sinceId));
    }

    private void setUntilId(String untilId) {
        queryParameters.add(new BasicNameValuePair("until_id", untilId));
    }

    private void changeUntilId(String untilId) {
        queryParameters.removeIf(item -> item.getName().equals("until_id"));
        this.setUntilId(untilId);
    }

    private void changeSinceId(String sinceId) {
        queryParameters.removeIf(item -> item.getName().equals("since_id"));
        this.setSinceId(sinceId);
    }

    public String getDate() {
        Date date = new Date();
        date.setDate(date.getDate() - 7);
        date.setHours(date.getHours() + 6);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = ft.format(date);
        time = time.replace(" ", "T");
        time += "Z";
        return time;
    }

    public String search() throws IOException, URISyntaxException {
        String searchResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/recent");
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            searchResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return searchResponse;
    }

    public void save(String content, String filePath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        bw.write(content);
        bw.close();
    }

    public void getWeekTweets() throws IOException, URISyntaxException {
        /**
         * get week tweets
         * auto save
         */
        this.setCreatedTime();
        this.setEndTime(this.getDate());
        String re = this.search();
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(re, Map.class);
        Map meta = (Map) map.get("meta");
        BigInteger since_id = new BigInteger((String) meta.get("oldest_id"));
        this.removeEndTime();
        this.setMaxResults("100");
        int n = 0;
        String oldestId;
        do {
            re = this.search();
            map = objectMapper.readValue(re, Map.class);
            meta = (Map) map.get("meta");
            this.save(re, this.queryParameters.get(0).getValue().split(":")[1] + n + ".json");
            n += 100;
            oldestId = (String) meta.get("oldest_id");
            this.changeUntilId(oldestId);
        } while (new BigInteger(oldestId).compareTo(since_id) > 0);

    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        Crawler crawler = new Crawler("WayVSubs2019");
        // set max results 10~100
        crawler.setMaxResults("100");
        // only 10
        String re = crawler.search();
        crawler.save(re, "emoji100.json");
    }
}