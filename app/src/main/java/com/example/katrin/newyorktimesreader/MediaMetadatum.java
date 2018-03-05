package com.example.katrin.newyorktimesreader;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MediaMetadatum {

    @SerializedName("url")
    @Expose
    String imageUrl;
    @SerializedName("height")
    @Expose
    int height;
}
