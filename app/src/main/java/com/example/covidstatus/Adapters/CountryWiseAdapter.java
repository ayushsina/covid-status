package com.example.covidstatus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.covidstatus.Models.CountryWiseModel;
import com.example.covidstatus.R;
import com.example.covidstatus.WorldDataActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountryWiseAdapter extends RecyclerView.Adapter<CountryWiseAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<CountryWiseModel> countryWiseModelArrayList;


    public CountryWiseAdapter(Context mContext, ArrayList<CountryWiseModel> countryWiseModelArrayList) {
        this.mContext = mContext;
        this.countryWiseModelArrayList = countryWiseModelArrayList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_country_wise, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CountryWiseModel currentItem = countryWiseModelArrayList.get(position);
        String countryName = currentItem.getCountry();
        String countryTotal = currentItem.getConfirmed();
        String countryFlag = currentItem.getFlag();
        String countryRank = String.valueOf(position+1);
        int countryTotalInt = Integer.parseInt(countryTotal);
        Log.d("country rank", countryRank);
        holder.tv_rankTextView.setText(countryRank+".");
        Log.d("country total cases int", String.valueOf(countryTotalInt));
        holder.tv_countryTotalCases.setText(NumberFormat.getInstance().format(countryTotalInt));
        holder.tv_countryName.setText(countryName);
        Glide.with(mContext).load(countryFlag).diskCacheStrategy(DiskCacheStrategy.DATA).into(holder.iv_flagImage);


    }

    @Override
    public int getItemCount() {
        return countryWiseModelArrayList==null || countryWiseModelArrayList.isEmpty() ? 0 : countryWiseModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_countryName, tv_countryTotalCases, tv_rankTextView;
        ImageView iv_flagImage;
        LinearLayout lin_country;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_countryName = itemView.findViewById(R.id.layout_country_wise_country_name_textview);
            tv_countryTotalCases = itemView.findViewById(R.id.layout_country_wise_confirmed_textview);
            iv_flagImage = itemView.findViewById(R.id.layout_country_wise_flag_imageview);
            tv_rankTextView = itemView.findViewById(R.id.layout_country_wise_country_rank);
            lin_country = itemView.findViewById(R.id.layout_country_wise_lin);
        }
    }
}
