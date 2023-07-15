package marketplace.objects;

public class Photo {
    private int photo_id;
    private String photo_url;

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public Photo(int photo_id, String photo_url) {
        this.photo_id = photo_id;
        this.photo_url = photo_url;
    }

    @Override
    public String toString() {
        return "photo: [" +
                "photo_id: " + photo_id +
                ", photo_url: " + photo_url +
                "]\n";
    }
}