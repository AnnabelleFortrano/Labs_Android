package com.example.androidlab5;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UrlRepository {
    private static UrlRepository instance;
    private List<Item> items;
    private String breedId;
    private VoidFunc notify = new VoidFunc() {
        @Override
        public void start() {

        }
    };
    private UrlRepository(String breedId) {
        items = new ArrayList<>();
        this.breedId = breedId;
    }

    public static UrlRepository createInstance(String breedId) {
        if(instance == null) {
            instance = new UrlRepository(breedId);
        }
        if(instance != null && instance.breedId != breedId) {
            instance = new UrlRepository(breedId);
        }
        return instance;
    }
    public List<String> getUrls() {
        List<String> urls = new LinkedList<>();
        for(Item item:items) {
            urls.add(item.getUrl());
        }
        return urls;
    }

    public void addUrls(List<String> urls) {
        urls.forEach(new Consumer<String>() {
            @Override
            public void accept(String elem) {
                items.add(new Item(elem));
            }
        });
    }
    public void setLiked(String url) {
        for(Item item: items) {
            if(item.getUrl() == url) {
                item.setLiked(1);
            }
        }
    }
    public void setDisliked(String url) {
        for(Item item : items) {
            if(item.getUrl() == url) {
                item.setLiked(-1);
            }
        }
    }
    public int getLiked(String url) {
        for(Item item: items) {
            if(item.getUrl() == url) {
                return item.getLiked();
            }
        }
        return 0;
    }
    public static UrlRepository getInstance() {
        return instance;
    }

    public void load() {
        HttpHandler httpHandler = new HttpHandler();
        httpHandler.execute(breedId);
    }

    public void setNotify(VoidFunc notify) {
        this.notify = notify;
    }

    public class HttpHandler extends AsyncTask<String, Void, List<String>> {

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            UrlRepository.getInstance().addUrls(strings);
            notify.start();
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            String breedId = strings[0];
            List<String> urls = new ArrayList<>();
            OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .addHeader("x-api-key", "8b399562-7d23-44ba-a61a-316c1fa086fe")
                        .url("https://api.thecatapi.com/v1/images/search?limit=9&order=Random&breed_ids=" + breedId)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    String body = response.body().string();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    List<Url> urlList = mapper.readValue(body, new TypeReference<List<Url>>(){});
                    for(Url url : urlList) {
                        urls.add(url.getUrl());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return urls;
        }
    }

}
