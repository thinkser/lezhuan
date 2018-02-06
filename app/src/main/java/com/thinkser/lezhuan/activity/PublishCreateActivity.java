package com.thinkser.lezhuan.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;

import com.google.gson.Gson;
import com.thinkser.core.adapter.RecyclerAdapter;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.core.utils.GlideEngine;
import com.thinkser.core.utils.SrcUtil;
import com.thinkser.core.view.CustomDialog;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityPublishCreateBinding;
import com.thinkser.lezhuan.dialog.HintDialog;
import com.thinkser.lezhuan.dialog.ProgressDialog;
import com.thinkser.lezhuan.entity.FileEntity;
import com.thinkser.lezhuan.entity.Publish;
import com.thinkser.lezhuan.entity.Store;
import com.thinkser.lezhuan.item.PublishImageItem;
import com.thinkser.lezhuan.model.PublishModel;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 新建/编辑广告
 */

public class PublishCreateActivity extends BaseActivity<AppData, ActivityPublishCreateBinding> {

    private PublishModel model;
    private ProgressDialog progressDialog;

    private Publish publish;
    private List<String> photos;//图片链接
    private String storeId;//对应店铺的id
    private static final int PHOTO_MAX_SIZE = 4;
    private static final int REQUEST_SELECT_STORE = 1;
    private static final int REQUEST_SELECT_IMAGE = 2;

    @Override
    protected int getLayout() {
        return R.layout.activity_publish_create;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        model = new PublishModel(activity);
        progressDialog = new ProgressDialog(this);

        publish = (Publish) intent.getSerializableExtra(CustomKey.info);
        if (publish == null) {
            publish = new Publish();
            data.adapter.set(new RecyclerAdapter(R.layout.item_publish_image));
            data.photos.add(getItem(null));
            data.adapter.get().addNew(data.photos);
        } else {
            data.publishTitle.set(publish.getTitle());
            data.content.set(publish.getContent());
            data.prizeCount.set(publish.getPrizeCount());
            showStore(publish.getStoreId());
            showPhotos(publish.getPhotos());
        }
    }

    //显示图片
    private void showPhotos(List<String> photos) {

    }

    //显示店铺信息
    private void showStore(String storeId) {

    }

    //清空服务器上的图片
    private void clearPhohts() {

    }


    //获取图片列表项
    private PublishImageItem getItem(File file) {
        PublishImageItem item = new PublishImageItem(file);
        item.setListener(new PublishImageItem.OnPublishImageItemClickListener() {
            @Override
            public void onClick() {
                if (file == null) {//添加图片
                    toSelect();
                } else {// 删除图片
                    data.photos.remove(item);
                    data.adapter.get().addNew(data.photos);
                }
            }
        });
        return item;
    }

    //跳转到图片选择界面
    private void toSelect() {
        Matisse.from(activity)
                .choose(MimeType.allOf()) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(PHOTO_MAX_SIZE - data.photos.size() + 1) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_SELECT_IMAGE); // 设置作为标记的请求码
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //选择店铺返回结果
        if (requestCode == REQUEST_SELECT_STORE && resultCode == RESULT_OK) {
            Store store = (Store) intent.getSerializableExtra(CustomKey.info);
            if (store != null) {
                data.storeName.set(store.getStoreName());
                data.storePhone.set(store.getStorePhone());
                data.storeAddress.set(store.getStoreAddress());
                data.showStore.set(true);
                storeId = store.getObjectId();
            }
        }
        //图片选择器返回结果
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
            List<Uri> selected = Matisse.obtainResult(intent);
            for (Uri uri : selected) {
                String path = SrcUtil.getPath(this, uri);
                if (data.photos.size() == PHOTO_MAX_SIZE) {//最后一个直接替换
                    int position = PHOTO_MAX_SIZE - 1;
                    data.photos.set(position, getItem(new File(path)));
                } else {
                    int position = data.photos.size() - 1;
                    data.photos.add(position, getItem(new File(path)));
                }
            }
            data.adapter.get().addNew(data.photos);
        }
    }

    @Override
    public void back() {
        new HintDialog(this, "确定要放弃编辑吗？")
                .setCancelable(true)
                .setRightButton("放 弃", R.color.colorTheme)
                .setOnHintClickListener(new HintDialog.OnClickListener() {
                    @Override
                    public void onLeftClick(CustomDialog dialog) {

                    }

                    @Override
                    public void onRightClick(CustomDialog dialog) {
                        activity.finish();
                    }
                });
    }

    //点击选择店铺
    public void selectStore() {
        Intent intent = new Intent(activity, StoreActivity.class);
        intent.putExtra("titleText", "选择店铺");
        startActivityForResult(intent, REQUEST_SELECT_STORE);
    }

    //增加奖品数量
    public void plus() {
        int money = data.money.get();
        int prizeCount = data.prizeCount.get();
        if (prizeCount < 10) {
            prizeCount++;
            money += 20;
            data.prizeCount.set(prizeCount);
            data.money.set(money);
        }
    }

    //减少奖品数量
    public void subtract() {
        int money = data.money.get();
        int prizeCount = data.prizeCount.get();
        if (data.money.get() > 0) {
            prizeCount--;
            money -= 20;
            data.prizeCount.set(prizeCount);
            data.money.set(money);
        }
    }

    //去支付
    public void pay() {
        savePhotos();
//        if (data.money.get() == 0) {
//            savePhotos();
//        } else {
//
//        }
    }

    //上传图片
    private void savePhotos() {
        progressDialog.showProgressDialog("请稍候", false);
        photos = new ArrayList<>();
        for (PublishImageItem item : data.photos) {
            File file = item.file.get();
            if (file != null) {
                model.uploadFile(preferencesUtil.getString(CustomKey.userId), file,
                        new BaseObserver<FileEntity>(progressDialog.dialog) {
                            @Override
                            protected void onSuccess(FileEntity fileEntity) {
                                log("" + new Gson().toJson(fileEntity));
                            }
                        });
            }
        }


//        savePublish();
    }

    //将发布信息上传至后台
    private void savePublish() {
        publish.setUserId(preferencesUtil.getString(CustomKey.userId));
        publish.setTitle(data.publishTitle.get());
        publish.setContent(data.content.get());
        publish.setStoreId(storeId);
        publish.setPrizeCount(data.prizeCount.get());
        publish.setPhotos(photos);
        model.createPublish(publish, new BaseObserver<Publish>(progressDialog.dialog) {
            @Override
            protected void onSuccess(Publish publish) {
                activity.setResult(RESULT_OK);
                activity.finish();
            }
        });
    }

}
