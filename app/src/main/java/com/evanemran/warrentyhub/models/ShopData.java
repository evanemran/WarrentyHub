package com.evanemran.warrentyhub.models;

public class ShopData {
    String shopId = "";
    String shopName = "";
    String shopNumber = "";
    String shopAddress = "";

    public ShopData(String shopId, String shopName, String shopNumber, String shopAddress) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopNumber = shopNumber;
        this.shopAddress = shopAddress;
    }

    public ShopData() {
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(String shopNumber) {
        this.shopNumber = shopNumber;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
}
