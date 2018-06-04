/*
 * This project is part of Android Developer Nanodegree Scholarship Program by
 * Udacity and Google
 * Created by Samuela Anastasi
 */
package com.example.android.bakingrecipes.utilities;

import android.content.Context;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.example.android.bakingrecipes.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.android.bakingrecipes.utilities.Constants.BROWNIES;
import static com.example.android.bakingrecipes.utilities.Constants.BROWNIES_RES;
import static com.example.android.bakingrecipes.utilities.Constants.CHEESECAKE;
import static com.example.android.bakingrecipes.utilities.Constants.CHEESECAKE_RES;
import static com.example.android.bakingrecipes.utilities.Constants.DEFAULT_RES;
import static com.example.android.bakingrecipes.utilities.Constants.NO_INFO;
import static com.example.android.bakingrecipes.utilities.Constants.NUTELLA;
import static com.example.android.bakingrecipes.utilities.Constants.NUTELLA_RES;
import static com.example.android.bakingrecipes.utilities.Constants.YELLOW;
import static com.example.android.bakingrecipes.utilities.Constants.YELLOW_RES;

// Static methods class for misc utilities
public class Utils {

    private Utils() {}

    private static boolean stringContains(String anyString, String pattern) {
        return anyString.toLowerCase().contains(pattern.toLowerCase());
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }
    public static boolean isPhoneLand(Context context) {
        return context.getResources().getBoolean(R.bool.isPhoneLand);
    }

    public static boolean isTabletLand(Context context) {
        return context.getResources().getBoolean(R.bool.isTabletLand);
    }

    public static int getDrawableRes(String anyString) {
        int imgRes;
        if(stringContains(anyString, NUTELLA)) {
            imgRes = NUTELLA_RES;
        } else if (stringContains(anyString, BROWNIES)) {
            imgRes = BROWNIES_RES;
        } else if(stringContains(anyString, YELLOW)) {
            imgRes = YELLOW_RES;
        }
        else if(stringContains(anyString, CHEESECAKE)) {
            imgRes = CHEESECAKE_RES;
        } else {
            imgRes = DEFAULT_RES;
        }

        return imgRes;
    }

    public static String takeOffStartingDigits(String anyString) {
        String newString = NO_INFO;
        if (anyString != null) {
            Pattern pattern = Pattern.compile("^\\d+\\.");
            Matcher matcher = pattern.matcher(anyString);
            if (matcher.find()) {
                newString = anyString.substring(matcher.end());
            } else {
                newString = anyString;
            }
        }

        return newString.trim();
    }

    public static String getMimeType(String url) {
        try {
            String type = null;
            String extension = url.substring(url.lastIndexOf(".") + 1, url.length());
            Log.i("extension", "ext : " + extension);
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
            return type;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isImageType(String anyString) {
        if (anyString != null) {
            String matchingString = getMimeType(anyString);
            if (matchingString != null) {
                Pattern pattern = Pattern.compile("jpg|png|gif|webp");
                Matcher matcher = pattern.matcher(matchingString);
                if (matcher.matches()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isVideoType(String anyString) {
        if (anyString != null) {
            String matchingString = getMimeType(anyString);
            if (matchingString != null) {
                Pattern pattern = Pattern.compile("mp4|webm");
                Matcher matcher = pattern.matcher(matchingString);
                if (matcher.matches()) {
                    return true;
                }
            }
        }
        return false;
    }
}
