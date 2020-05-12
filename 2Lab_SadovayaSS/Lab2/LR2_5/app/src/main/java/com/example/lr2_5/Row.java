package com.example.lr2_5;

public class Row {
    private String name;
    private String imgUrl;

    public Row(String name, String imgUrl){
        this.name=name;
        this.imgUrl=imgUrl;
    }

    public String getName(){
        return name;
    }

    public String getImgUrl(){
        return imgUrl;
    }
}
