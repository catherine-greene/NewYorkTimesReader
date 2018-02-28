package com.example.katrin.newyorktimesreader;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GetArticlesTask extends AsyncTask<GetArticlesTask.Category, Void, String> {

    private final String API_KEY = "84360f4cbd464edb862b7d2199a79fac";
    private final String API_URL[] = {
            "https://api.nytimes.com/svc/mostpopular/v2/mostemailed/all-sections/30.json",
            "https://api.nytimes.com/svc/mostpopular/v2/mostshared/all-sections/30.json",
            "https://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/30.json"
    };
    private OnTaskResponse delegate = null;

    GetArticlesTask(OnTaskResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Category... categories) {

        String urlStr = API_URL[categories[0].ordinal()];

        try {

            URL url = new URL(urlStr + "?api-key=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();

            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        List<Article> articleList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                JSONObject object = results.getJSONObject(i);
                String fullTextUrl = object.getString("url");
                String section = object.getString("section");
                String byLine = object.getString("byline");
                String title = object.getString("title");
                String abstractText = object.getString("abstract");
                String publishedDate = object.getString("published_date");
                JSONArray mediaArr = object.getJSONArray("media");
                String imageUrl = null;

                for (int j = 0; j < mediaArr.length(); j++) {

                    JSONObject mediaObj = mediaArr.getJSONObject(j);
                    if (!mediaObj.getString("type").equals("image")) {
                        continue;
                    }

                    JSONArray metadataArr = mediaObj.getJSONArray("media-metadata");
                    JSONObject metadataObj = metadataArr.getJSONObject(0);
                    imageUrl = metadataObj.getString("url");
                    break;
                }


                Article article = new Article(fullTextUrl, section, byLine, title, abstractText, publishedDate, imageUrl);
                articleList.add(article);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        delegate.createArticlesList(articleList);
    }

    enum Category {
        mostEmailed, mostShared, mostViewed
    }

    public interface OnTaskResponse {
        void createArticlesList(List<Article> articleList);
    }

}
