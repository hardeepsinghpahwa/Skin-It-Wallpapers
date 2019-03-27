package com.pahwa.hardeep.wallpapers;

public class categories_card_details {

    String image,text;

    public categories_card_details() {
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public categories_card_details(String image, String text) {

        this.image = image;
        this.text = text;
    }

}
