package com.icondev76.redditpics.model.children;

public class Data {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Data{" +
                "url='" + url + '\'' +
                '}';
    }
}
