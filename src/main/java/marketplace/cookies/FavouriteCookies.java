package marketplace.cookies;

import marketplace.constants.CookieConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class FavouriteCookies {
    public static List<Integer> getFavouriteIDs(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie favoritesCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookieConstants.FAVOURITE_COOKIE_NAME)) {
                    favoritesCookie = cookie;
                    break;
                }
            }
        }
        if (favoritesCookie == null)
            return new ArrayList<>();
        String currentFavorites = favoritesCookie.getValue();
        String[] integerStrings = currentFavorites.split(CookieConstants.FAVOURITE_COOKIE_SPLIT);
        List<Integer> integerList = new ArrayList<>();
        for (String integerString : integerStrings) {
            if (!integerString.isEmpty()) {
                int number = Integer.parseInt(integerString);
                integerList.add(number);
            }
        }
        return integerList;
    }

    public static boolean isFavourite(int post_id, HttpServletRequest request, HttpServletResponse response) {
        return getFavouriteIDs(request, response).contains(post_id);
    }

    public static void addFavourite(int post_id, HttpServletRequest request, HttpServletResponse response) {
        if(isFavourite(post_id, request, response))
            return;
        Cookie[] cookies = request.getCookies();
        Cookie favoritesCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookieConstants.FAVOURITE_COOKIE_NAME)) {
                    favoritesCookie = cookie;
                    break;
                }
            }
        }
        if (favoritesCookie != null) {
            String currentFavorites = favoritesCookie.getValue();
            favoritesCookie.setValue(currentFavorites + post_id + CookieConstants.FAVOURITE_COOKIE_SPLIT);
        } else {
            favoritesCookie = new Cookie(CookieConstants.FAVOURITE_COOKIE_NAME, Integer.toString(post_id) + CookieConstants.FAVOURITE_COOKIE_SPLIT);
        }
        favoritesCookie.setMaxAge(CookieConstants.FAVOURITE_COOKIE_LIFE);
        favoritesCookie.setPath("/");
        response.addCookie(favoritesCookie);
    }

    public static void removeFavourite(int post_id, HttpServletRequest request, HttpServletResponse response) {
        if(!isFavourite(post_id, request, response))
            return;
        Cookie[] cookies = request.getCookies();
        Cookie favoritesCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookieConstants.FAVOURITE_COOKIE_NAME)) {
                    favoritesCookie = cookie;
                    break;
                }
            }
        }
        ArrayList<Integer> postsIDs = (ArrayList<Integer>) getFavouriteIDs(request, response);
        postsIDs.remove((Integer) post_id);
        StringBuilder newValue = new StringBuilder();
        for(Integer id : postsIDs)
            newValue.append(id).append(CookieConstants.FAVOURITE_COOKIE_SPLIT);
        favoritesCookie.setValue(newValue.toString());
        favoritesCookie.setMaxAge(CookieConstants.FAVOURITE_COOKIE_LIFE);
        favoritesCookie.setPath("/");
        response.addCookie(favoritesCookie);
    }


}
