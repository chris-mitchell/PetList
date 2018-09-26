package com.cmitchell687.petlist.ui.bindings;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageViewBindings {

    @BindingAdapter(value = {"fileUri", "placeHolderDrawable"})
    public static void setBackground(ImageView view, String fileUri, Drawable placeHolderDrawable) {
        Picasso.get()
                .load(Uri.parse("file://" + fileUri))
                .placeholder(placeHolderDrawable)
                .fit()
                .into(view);
    }

}
