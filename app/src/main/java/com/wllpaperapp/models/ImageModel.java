package com.wllpaperapp.models;

import java.util.List;

public class ImageModel {

    private ImageUrlModel src;

    public ImageModel(ImageUrlModel src) {
        this.src = src;
    }

    public ImageUrlModel getSrc() {
        return src;
    }
}
