package com.example.katrin.newyorktimesreader;

import android.os.AsyncTask;
import android.support.v4.util.Pair;

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


public class GetArticlesTask extends AsyncTask<GetArticlesTask.Category, Void, Pair<GetArticlesTask.Category, String>> {

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
    protected Pair<GetArticlesTask.Category, String> doInBackground(Category... categories) {

        String urlStr = API_URL[categories[0].ordinal()];
        final String API_KEY = "84360f4cbd464edb862b7d2199a79fac";

        try {

            URL url = new URL(urlStr + "?api-key=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {

                try {

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return new Pair<>(categories[0], stringBuilder.toString());

                } finally {
                    urlConnection.disconnect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        new GetArticlesTask(delegate).execute(categories[0]);

        return null;
    }

    @Override
    protected void onPostExecute(Pair<GetArticlesTask.Category, String> pair) {

        if (pair == null) {
            return;
        }
        List<Article> articleList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(pair.second);
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                JSONObject object = results.getJSONObject(i);
                String fullTextUrl = object.getString("url");
                String section = object.getString("section");
                String byLine = object.getString("byline");
                String title = object.getString("title");
                String abstractText = object.getString("abstract");
                String publishedDate = object.getString("published_date");

                String imageUrl = null;
                Object mediaO = object.get("media");
                if (mediaO instanceof JSONArray) {
                    JSONArray mediaArr = (JSONArray) mediaO;

                    for (int j = 0; j < mediaArr.length(); j++) {

                        JSONObject mediaObj = mediaArr.getJSONObject(j);
                        if (!mediaObj.getString("type").equals("image")) {
                            continue;
                        }

                        JSONArray metadataArr = mediaObj.getJSONArray("media-metadata");
                        int maxHeight = 0;
                        for (int k = 0; k < metadataArr.length(); k++) {
                            JSONObject metadataObj = metadataArr.getJSONObject(k);
                            int height = metadataObj.getInt("height");
                            if (height < maxHeight || height > 320) {
                                continue;
                            }
                            maxHeight = height;
                            imageUrl = metadataObj.getString("url");
                        }
                        break;
                    }
                }

                Article article = new Article(fullTextUrl, section, byLine, title, abstractText, publishedDate, imageUrl);
                articleList.add(article);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        delegate.createArticlesList(articleList, pair.first);
    }

    enum Category {
        mostEmailed, mostShared, mostViewed
    }

    public interface OnTaskResponse {
        void createArticlesList(List<Article> articleList, Category category);
    }

}
