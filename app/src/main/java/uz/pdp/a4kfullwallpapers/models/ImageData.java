package uz.pdp.a4kfullwallpapers.models;

import java.io.Serializable;

public class ImageData implements Serializable {


    private String imageUrl;
    private String author;
    private String download;
    private String size;
    private String dimension;
    private boolean liked;

    public ImageData(String imageUrl, String author, String download, String size, String dimension, boolean liked) {
        this.imageUrl = imageUrl;
        this.author = author;
        this.download = download;
        this.size = size;
        this.dimension = dimension;
        this.liked = liked;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
