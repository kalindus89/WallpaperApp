package com.wllpaperapp.models;

import java.util.List;

public class SearchModel {

        private List<ImageModel> photos;

    public SearchModel(List<ImageModel> photos) {
        this.photos = photos;
    }

    public List<ImageModel> getPhotos() {
        return photos;
    }
}
