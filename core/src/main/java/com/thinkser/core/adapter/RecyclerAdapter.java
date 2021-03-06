package com.thinkser.core.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkser.core.BR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * 通用的RecyclerAdapter。
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private int layout;
    private ArrayList data;

    public RecyclerAdapter(int layout) {
        this.layout = layout;
        this.data = new ArrayList();
    }

    public void refresh(ArrayList newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public void loadingMore(ArrayList moreData) {
        data.addAll(moreData);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                layout, parent, false);

        ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.presenter, data.get(position));
        holder.getBinding().setVariable(BR.position, position);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}
