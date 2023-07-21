package marketplace.utils;

public class PostValidator {
    public static boolean validateTitle(String title){
        if(title == null || title.equals(""))
            return false;
        return true;
    }
    public static double priceValidate(String price){
        if(price == null || price.length() == 0)
            return 0;
        return Double.parseDouble(price);
    }
    public static String descriptionValidate(String description){
        if(description == null)
            return "";
        return description;
    }
}
