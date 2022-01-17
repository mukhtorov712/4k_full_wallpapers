package uz.pdp.a4kfullwallpapers.models;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private String title;
    private List<ImageData> list;

    public Category(String title, List<ImageData> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImageData> getList() {
        return list;
    }

    public void setList(List<ImageData> list) {
        this.list = list;
    }
}
