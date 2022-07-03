package com.evanemran.warrentyhub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evanemran.warrentyhub.R;
import com.evanemran.warrentyhub.models.CategoryData;
import com.evanemran.warrentyhub.models.ShopData;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<CategoryData> {
    public CategorySpinnerAdapter(Context context,
                                  List<CategoryData> algorithmList)
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_category, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.text_view);
        CategoryData currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getCategoryName());
        }
        return convertView;
    }
}
