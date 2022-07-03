package com.evanemran.warrentyhub.models;

import com.evanemran.warrentyhub.R;

public enum NavMenu {
    HOME("Home", R.drawable.ic_product),
    CATEGORIES("Categories", R.drawable.ic_product),
    PRODUCTS("Products", R.drawable.ic_product),
    SHOPS("Shops", R.drawable.ic_product),
    SETTINGS("Settings", R.drawable.ic_product),
    LOGOUT("SIgn-Out", R.drawable.ic_product)
    ;

    private String title = "";
    private int icon = 0;

    NavMenu(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
