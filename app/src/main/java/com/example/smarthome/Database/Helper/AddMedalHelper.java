package com.example.smarthome.Database.Helper;

import com.example.smarthome.Database.AddModel;
/**
 * @description 弃用
 */
public class AddMedalHelper extends AddModel {
    int image;
    String title,description;

    public AddMedalHelper(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
