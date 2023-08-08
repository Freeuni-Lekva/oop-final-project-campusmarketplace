package marketplace.dao.daoInterfaces;

import marketplace.objects.Photo;

import java.util.ArrayList;

public interface PhotoDAOInterface {
    void deletePhoto(int photo_id);
    void addPhoto(int post_id, String photo_url);
    ArrayList<Photo> getPhotos(int post_id);

}
