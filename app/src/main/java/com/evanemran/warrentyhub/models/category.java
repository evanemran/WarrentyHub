package com.evanemran.warrentyhub.models;

import androidx.fragment.app.Fragment;

import com.evanemran.warrentyhub.R;
import com.evanemran.warrentyhub.fragments.ProductFragment;

import java.io.Serializable;

public enum category implements Serializable {
    ELECTRONICS("Electronics", R.drawable.ic_product, new ProductFragment()),
    MOBILE("Mobile", R.drawable.ic_product, new ProductFragment()),
    WATCHES("Watches", R.drawable.ic_product, new ProductFragment()),
    ACCESSORIES("Accessories", R.drawable.ic_product, new ProductFragment()),
    APPLIANCES("Appliances", R.drawable.ic_product, new ProductFragment()),
    SERVICES("Services", R.drawable.ic_product, new ProductFragment()),
    MISC("Misc.", R.drawable.ic_product, new ProductFragment()),
    ;


    private String title = "";
    private int icon = 0;
    private Fragment fragment = null;

    category(String title, int icon, Fragment fragment) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
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

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
