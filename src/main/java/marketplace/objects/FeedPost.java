package marketplace.objects;

import java.sql.Date;

public class FeedPost {
    private int post_id;
    private String photo_address;
    private String title;
    private Date date;
    private double price;

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setPost_id(int post_id) {
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

    public int getPost_id() {
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

    public FeedPost(int post_id, String photo_address, String title, double price, Date date) {
        this.post_id = post_id;
        this.photo_address = photo_address;
        this.title = title;
        this.price = price;
        this.date = date;
    }

    @Override
    public String toString() {
        return "feedPost: [" +
                "post_id: " + Integer.toString(post_id) +
                ", photo_address: " + photo_address +
                ", title: " + title +
                ", price: " + Double.toString(price) +
                ", Date: " + date.toString() +
                "]\n";
    }
}