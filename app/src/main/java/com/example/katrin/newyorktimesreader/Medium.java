package com.example.katrin.newyorktimesreader;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class Medium {

    @SerializedName("media-metadata")
    @Expose
    List<MediaMetadatum> mediaMetadata;
}
