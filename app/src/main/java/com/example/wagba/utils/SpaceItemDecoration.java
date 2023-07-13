package com.example.wagba.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    public void getItemsOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent) {
        if (parent.getChildLayoutPosition(view) % 2 != 0) {
            outRect.top = 50;
            outRect.bottom = -50;
        }

    }
}
