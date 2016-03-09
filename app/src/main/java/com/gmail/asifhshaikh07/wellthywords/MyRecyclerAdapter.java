package com.gmail.asifhshaikh07.wellthywords;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Asif on 3/6/2016.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder>{

    private List<WordItem> wordItemList;
    private Context mContext;

    public MyRecyclerAdapter(Context context, List<WordItem> wordItemList) {
        this.wordItemList = wordItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        WordItem wordItem = wordItemList.get(i);

        //Download image using picasso library
    Picasso.with(mContext).load(wordItem.getThumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.thumbnail);

        //Setting text view title
        customViewHolder.titleView.setText(Html.fromHtml(wordItem.getTitle()));
        customViewHolder.meaningView.setText(Html.fromHtml(wordItem.getMeaning()));
    }

    @Override
    public int getItemCount() {
        return (null != wordItemList ? wordItemList.size() : 0);
    }
}
