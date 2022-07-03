package com.evanemran.warrentyhub.listeners;

import android.net.Uri;

import com.evanemran.warrentyhub.models.ImageUri;
import com.evanemran.warrentyhub.models.PostData;

import java.util.List;


public interface PostListener {
    void onPostClicked(PostData data, List<ImageUri> imageUri);
}
