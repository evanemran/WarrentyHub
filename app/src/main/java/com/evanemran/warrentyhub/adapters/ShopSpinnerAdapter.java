package com.evanemran.warrentyhub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.warrentyhub.R;
import com.evanemran.warrentyhub.models.ShopData;

import java.util.ArrayList;
import java.util.List;

public class ShopSpinnerAdapter extends ArrayAdapter<ShopData> {
    public ShopSpinnerAdapter(Context context,
                              List<ShopData> algorithmList)
    {
        super(context, 0, algorithmList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_shops, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.text_view);
        ShopData currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getShopName());
        }
        return convertView;
    }
}
