package com.tangqi.listviewtest_new;

/**
 * Created by Timi on 2017/11/7.
 */

public class Fruits {
    private String name;
    private int image;

    public Fruits(String name,int image){
        this.name=name;
        this.image=image;
    };

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
