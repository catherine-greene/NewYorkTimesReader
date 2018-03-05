package com.example.katrin.newyorktimesreader;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;

public class Article implements Serializable {

    @SerializedName("url")
    public String fullTextUrl;

    @SerializedName("byline")
    public String byLine;

    @SerializedName("title")
    public String title;

    @SerializedName("abstract")
    public String abstractText;

    @SerializedName("published_date")
    public String published_date;

    @SerializedName("media")
    @JsonAdapter(MediaSerializer.class)
    public String imageUrl;

    public Article() {
    }

    public Article(String fullTextUrl, String byLine, String title, String abstractText, String published_date, String imageUrl) {
        this.fullTextUrl = fullTextUrl;
        this.byLine = byLine;
        this.title = title;
        this.abstractText = abstractText;
        this.published_date = published_date;
        this.imageUrl = imageUrl;
    }

    public static class MediaSerializer implements JsonDeserializer<String> {
        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            int maxHeight = 0;
            String url = null;

            if (json.isJsonArray()) {

                JsonArray mediaList = json.getAsJsonArray();
                for (JsonElement mediaEl : mediaList) {
                    JsonObject media = mediaEl.getAsJsonObject();
                    if (!"image".equals(media.get("type").getAsString())) {
                        continue;
                    }

                    JsonElement metadataListEl = media.get("media-metadata");
                    if (metadataListEl.isJsonArray()) {
                        for (JsonElement itemEl : metadataListEl.getAsJsonArray()) {
                            JsonObject item = itemEl.getAsJsonObject();
                            int height = item.get("height").getAsInt();
                            if (height > 320 || height < maxHeight) {
                                continue;
                            }
                            maxHeight = height;
                            url = item.get("url").getAsString();
                        }
                    }
                }
            }

            return url;
        }
    }
}
