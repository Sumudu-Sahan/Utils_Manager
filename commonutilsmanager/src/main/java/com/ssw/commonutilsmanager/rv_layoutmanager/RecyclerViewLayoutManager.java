package com.ssw.commonutilsmanager.rv_layoutmanager;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;

public class RecyclerViewLayoutManager {
    private static final String TAG = "RecyclerViewLayoutManager";

    private static RecyclerViewLayoutManager recyclerViewLayoutManager;

    public static final int ORIENTATION_VERTICAL = 0;
    public static final int ORIENTATION_HORIZONTAL = 1;

    private LinearLayoutManager linearLayoutManager;

    public static synchronized RecyclerViewLayoutManager getInstance() {
        if (recyclerViewLayoutManager == null) {
            recyclerViewLayoutManager = new RecyclerViewLayoutManager();
        }
        return recyclerViewLayoutManager;
    }

    public boolean setLayoutManager(Context context, RecyclerView recyclerView, int orientation) {
        try {
            switch (orientation) {
                case ORIENTATION_VERTICAL:
                default:
                    linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    break;

                case ORIENTATION_HORIZONTAL:
                    linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                    break;
            }
            recyclerView.setLayoutManager(linearLayoutManager);
            return true;
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
