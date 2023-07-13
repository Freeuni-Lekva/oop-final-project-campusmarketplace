package marketplace.objects;

import java.util.Date;

public class FeedPost {
    private String post_id;
    private Photo photo;
    private String photo_address;
    private String title;
    private String date;
    private double price;

    public FeedPost(String postId, String photoAddress, String title, Double price, Date date) {
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setPhoto_address(String photo_address) {
        this.photo_address = photo_address;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getPhoto_address() {
        return photo_address;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public FeedPost() {

    }

    public FeedPost(String post_id, String photo_address, String title, double price, String date) {
        this.post_id = post_id;
        this.photo_address = photo_address;
        this.title = title;
        this.price = price;
        this.date = date;
    }


}