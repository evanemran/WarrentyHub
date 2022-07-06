package com.evanemran.warrentyhub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.warrentyhub.R;
import com.evanemran.warrentyhub.listeners.ClickListener;
import com.evanemran.warrentyhub.models.PostData;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePostAdapter extends RecyclerView.Adapter<HomeViewHolder>{

    Context context;
    List<PostData> list;
    ClickListener<PostData> listener;

    public HomePostAdapter(Context context, List<PostData> list, ClickListener<PostData> listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        final PostData data = list.get(position);

        holder.textView_prodName.setText(data.getPostProductName());
        holder.textView_postedDetail.setText("Added by " + data.getPostedBy() + " on " + data.getPosTTime());
        try{
            Picasso.get().load(data.getProDuctImage()).into(holder.imageView_prod);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.textView_postedWarrantyLeft.setText(getRemainingDays(data.getWarrantyDate(), data.getPosTTime()));
    }

    private String getRemainingDays(String warrantyDate, String posTTime) {
        SimpleDateFormat sdfWarranty = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfCurrent = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
        try {
            Date dateWarranty = sdfWarranty.parse(warrantyDate);
            Date dateCurrent = sdfCurrent.parse(posTTime);
            long diff = dateWarranty.getTime() - dateCurrent.getTime();
            return "Warranty left " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + " days";
        } catch (ParseException e) {
            e.printStackTrace();
            return "None";
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class HomeViewHolder extends RecyclerView.ViewHolder {

    CardView home_container;
    TextView textView_prodName, textView_postedDetail, textView_postedWarrantyLeft;
    ImageView imageView_prod;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView_prod = itemView.findViewById(R.id.imageView_prod);
        textView_prodName = itemView.findViewById(R.id.textView_prodName);
        textView_postedDetail = itemView.findViewById(R.id.textView_postedDetail);
        home_container = itemView.findViewById(R.id.home_container);
        textView_postedWarrantyLeft = itemView.findViewById(R.id.textView_postedWarrantyLeft);
    }
}
