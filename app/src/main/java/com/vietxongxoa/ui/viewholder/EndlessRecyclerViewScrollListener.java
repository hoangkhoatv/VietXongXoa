package com.vietxongxoa.ui.viewholder;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;


public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    // The current offset index of data you have loaded
    private int offset = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    boolean rich = false;

    private RecyclerView.LayoutManager mLayoutManager;

    protected EndlessRecyclerViewScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();
        Log.d("totalItemCount", String.valueOf(totalItemCount));

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            Log.d("AAA","AAA");
            this.offset = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            Log.d("BBB","BBB");
            loading = false;
            previousTotalItemCount = totalItemCount;
        }
//        Log.d("Pre Count ",String.valueOf(previousTotalItemCount));
//        Log.d("Total Count",String.valueOf(totalItemCount));
//        Log.d("Current",String.valueOf(offset));
//
//        Log.d("lastVisibleItemPosition",String.valueOf(lastVisibleItemPosition));
//        Log.d("Loadinf",String.valueOf(loading));
        // The minimum amount of items to have below your current scroll position
        // before loading more.
        int visibleThreshold = 10;
//        if (loading && (lastVisibleItemPosition == totalItemCount - 1) && (offset >= totalItemCount)) {
//            Log.d("CCC","CCC");
//            offset = offset - visibleThreshold;
//            loading = false;
//        }


        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too

        Log.d("lastVisibleItemPosition", String.valueOf(lastVisibleItemPosition));
        Log.d("totalItemCount", String.valueOf(totalItemCount));


        if ( !loading && (lastVisibleItemPosition ) >= (totalItemCount -1 )) {
            Log.d("DDD","DDD");
            onLoadMore(offset);
            offset = offset + visibleThreshold;
            loading = true;
            rich  = true;
        }

        Log.d("offset", String.valueOf(offset));

    }

    // Call this method whenever performing new searches
    public void resetState() {
        this.offset = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page);

}
