package com.tangqi.weixin;

/**
 * Created by Timi on 2018/1/30.
 * 实体类。
 */

public class Contacts {
    private int image;
    private String name;

    public Contacts(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

