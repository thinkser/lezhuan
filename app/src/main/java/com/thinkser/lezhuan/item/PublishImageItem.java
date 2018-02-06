package com.thinkser.lezhuan.item;

import android.databinding.ObservableField;

import java.io.File;

public class PublishImageItem {

    public final ObservableField<File> file = new ObservableField<>();

    private OnPublishImageItemClickListener listener;

    public PublishImageItem(File file) {
        this.file.set(file);
    }

    public void click() {
        listener.onClick();
    }

    public interface OnPublishImageItemClickListener {
        void onClick();
    }

    public void setListener(OnPublishImageItemClickListener listener) {
        this.listener = listener;
    }
}
