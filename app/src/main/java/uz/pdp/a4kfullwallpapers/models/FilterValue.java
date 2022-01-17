package uz.pdp.a4kfullwallpapers.models;

import android.graphics.Bitmap;

public class FilterValue {
    private Bitmap image;
    private String name;
    private int value;

    public FilterValue(Bitmap image, String name, int value) {
        this.image = image;
        this.name = name;
        this.value = value;
    }



    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
