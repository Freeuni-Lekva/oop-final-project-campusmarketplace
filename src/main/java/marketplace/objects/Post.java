package marketplace.objects;

import java.util.ArrayList;
import java.sql.Date;

public class Post {

    private int profile_id, post_id;
    private String title, description;
    private Date date;
    private double price;
    private ArrayList<Photo> photos;
    private boolean isProfilesPost = false;
    public boolean isProfilesPost() {
        return isProfilesPost;
    }

    public void setProfilesPost(boolean profilesPost) {
        isProfilesPost = profilesPost;
    }



    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }


    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
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


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Post(int profile_id, int post_id, String title, double price, String description, Date date) {
        this.profile_id = profile_id;
        this.post_id = post_id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Post: [" +
                "profile_id: " + profile_id +
                ", post_id: " + post_id +
                ", title: " + title +
                ", price: " + price +
                ", description: " + description +
                ", date: " + date + "]\n");
        if(photos!=null){
            for (Photo photo : photos) {
                result.append(photo.toString());
            }
        }
        return result.toString();
    }
}