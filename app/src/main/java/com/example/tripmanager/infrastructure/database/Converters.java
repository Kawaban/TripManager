package com.example.tripmanager.infrastructure.database;

import android.net.Uri;

import androidx.room.TypeConverter;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Converters {
    public static final String delimiter = ",";
    @TypeConverter
    public static String fromListToString(ArrayList<Uri> uriList) {
        StringBuilder stringBuilder = new StringBuilder();

        // Iterate through the list of URIs
        for (Uri uri : uriList) {
            stringBuilder.append(uri.toString()); // Append the URI string representation
            stringBuilder.append(delimiter); // Append the delimiter
        }

        // Remove the trailing delimiter if list is not empty
        if (!uriList.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length() - delimiter.length());
        }

        return stringBuilder.toString();
    }

    @TypeConverter
    public static ArrayList<Uri> fromStringToList(String data) {
        ArrayList<Uri> uriList = new ArrayList<>();

        // Split the string based on the delimiter
        String[] uriStrings = data.split(delimiter);


        // Iterate through the substrings and convert each one to URI
        for (String str : uriStrings) {
            Uri uri = Uri.parse(str.trim()); // Trim the substring to remove leading/trailing spaces
            uriList.add(uri);
        }

        return uriList;
    }
}
