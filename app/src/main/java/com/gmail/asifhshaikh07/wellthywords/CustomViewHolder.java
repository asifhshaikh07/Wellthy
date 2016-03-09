package com.gmail.asifhshaikh07.wellthywords;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Asif on 3/6/2016.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {

    protected ImageView thumbnail;
    protected TextView titleView;
    protected TextView meaningView;

    public CustomViewHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        this.titleView = (TextView) view.findViewById(R.id.title);
        this.meaningView = (TextView) view.findViewById(R.id.meaning);
    }
}
