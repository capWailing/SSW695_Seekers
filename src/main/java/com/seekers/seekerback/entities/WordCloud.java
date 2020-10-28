package com.seekers.seekerback.entities;

/**
 * program: seeker-back
 * description: word cloud entity
 * author: Zituo Yan
 * create: 2020-10-28
 **/
public class WordCloud {

    private String url;

    private String user;

    public WordCloud(String user){
        this.user = user;
        this.url = "http://"+ user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "WordCloud{" +
                "url='" + url + '\'' +
                '}';
    }
}
