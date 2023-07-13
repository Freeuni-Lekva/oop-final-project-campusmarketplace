package marketplace.objects;
import java.util.ArrayList;
import java.util.Date;

public class Post {

    private String profile_id, post_id;
    private String title, description, date;
    private double price;
    private ArrayList<Photo> photos;

    public Post(String profileId, String postId, String title, Double price, String description, Date date) {
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }


    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
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



    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Post() {

    }

    public Post(String profile_id, String post_id, String title, double price, String description) {
        this.profile_id = profile_id;
        this.post_id = post_id;
        this.title = title;
        this.price = price;
        this.description = description;
    }
}