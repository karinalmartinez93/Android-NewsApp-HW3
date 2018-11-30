package com.example.rkjc.news_app_2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String NEWS_API_URL = "https://newsapi.org/v1/articles";
    public static final String PARAM_SOURCE = "source";
    public static final String PARAM_SORTBY = "sortBy";
    public static final String PARAM_API = "apiKey";
    public static final String source = "the-next-web";
    public static final String sortBy = "latest";
    public static final String apiKey = "3d30046e498d45bbb912b9d1fe7d22d3";

    public static URL buildURL() {
        Uri builtUri = Uri.parse(NEWS_API_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, source)
                .appendQueryParameter(PARAM_SORTBY, sortBy)
                .appendQueryParameter(PARAM_API, apiKey)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl (URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
