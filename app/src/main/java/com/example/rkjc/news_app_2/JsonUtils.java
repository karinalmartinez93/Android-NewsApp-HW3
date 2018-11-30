package com.example.rkjc.news_app_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {
    public static ArrayList<NewsItem> parseNews(String jsonSearchResult){

        // Declare ArrayList object to store NewsItem from call
        ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();

        try{
            // Create JSON object from string containing the results from API call
            JSONObject allNewsJSONObject = new JSONObject(jsonSearchResult);

            // Get all articles from JSONObject
            JSONArray allNewsArticles = allNewsJSONObject.getJSONArray("articles");

            // for all articles in the allNewsArticles JSONArray get desired fields
            //
            for(int i = 0; i < allNewsArticles.length(); i++){
                JSONObject article = allNewsArticles.getJSONObject(i);

                // create NewsItem object for that article
                NewsItem newsItem = new NewsItem(
                        article.getString("author"),
                        article.getString("title"),
                        article.getString("description"),
                        article.getString("url"),
                        article.getString("urlToImage"),
                        article.getString("publishedAt")
                );

                // add the news item tp the newsItem array
                newsItems.add(newsItem);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        // Return list of newsItems from JSONObject
        return newsItems;
    }
}


