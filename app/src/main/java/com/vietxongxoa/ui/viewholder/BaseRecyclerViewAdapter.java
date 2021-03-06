package com.vietxongxoa.ui.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected LayoutInflater mInflater;
    protected List<T> mDataList;

    BaseRecyclerViewAdapter(@NonNull Context context) {
        mInflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
    }

    public void add(List<T> itemList) {
        mDataList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void set(List<T> dataList) {
        List<T> clone = new ArrayList<>(dataList);
        mDataList.clear();
        mDataList.addAll(clone);
        notifyDataSetChanged();
    }

    public T getItem(int pos){
        return mDataList.get(pos);
    }


    public int sizeData() {
        return mDataList.size();
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
