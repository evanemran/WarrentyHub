package com.evanemran.warrentyhub.models;

import android.net.Uri;

public class ImageUri {
    int imageCode = 0;
    Uri uri;

    public ImageUri(int imageCode, Uri uri) {
        this.imageCode = imageCode;
        this.uri = uri;
    }

    public int getImageCode() {
        return imageCode;
    }

    public void setImageCode(int imageCode) {
        this.imageCode = imageCode;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
