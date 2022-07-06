package com.evanemran.warrentyhub.models;

import java.util.ArrayList;
import java.util.List;

public class PostData {
    String postId = "";
    String postedBy = "";
    String postProductName = "";
    String postBody = "";
    String posTTime = "";
    CategoryData categoryData;

    public PostData() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public CategoryData getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(CategoryData categoryData) {
        this.categoryData = categoryData;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostProductName() {
        return postProductName;
    }

    public void setPostProductName(String postProductName) {
        this.postProductName = postProductName;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getPosTTime() {
        return posTTime;
    }

    public void setPosTTime(String posTTime) {
        this.posTTime = posTTime;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getInVoiceNo() {
        return inVoiceNo;
    }

    public void setInVoiceNo(String inVoiceNo) {
        this.inVoiceNo = inVoiceNo;
    }

    public String getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getProDuctImage() {
        return proDuctImage;
    }

    public void setProDuctImage(String proDuctImage) {
        this.proDuctImage = proDuctImage;
    }

    public String getInVoiceImage() {
        return inVoiceImage;
    }

    public void setInVoiceImage(String inVoiceImage) {
        this.inVoiceImage = inVoiceImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ShopData getShopData() {
        return shopData;
    }

    public void setShopData(ShopData shopData) {
        this.shopData = shopData;
    }

    public List<CommentData> getComments() {
        return comments;
    }

    public void setComments(List<CommentData> comments) {
        this.comments = comments;
    }

    boolean hasImage = false;
    String inVoiceNo = "";
    String warrantyDate = "";
    String proDuctImage = "";
    String inVoiceImage = "";
    int quantity = 1;
    ShopData shopData;
    List<CommentData> comments = new ArrayList<>();
}
