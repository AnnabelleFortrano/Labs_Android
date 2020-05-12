package com.example.androidlab5;

import java.util.LinkedList;
import java.util.List;

public class LikedRepository {
    private static LikedRepository instance;
    private LinkedList<String> urls;
    private LikedRepository() {
        urls = new LinkedList<>();
    }
    public static LikedRepository createInstance() {
        if(instance == null) {
            instance = new LikedRepository();
        }
        return instance;
    }
    public void addUrl(String url) {
        if(urls.size() == 10) {
            urls.pop();
        }
        urls.add(url);
    }
    public void deleteUrl(String url) {
        urls.remove(url);
    }
    public LinkedList<String> getUrls() {
        return urls;
    }
}
