package com.thinkser.lezhuan.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;

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
import java.util.Map;

/**
 * 新建/编辑广告
 */

public class PublishCreateActivity extends BaseActivity<AppData, ActivityPublishCreateBinding> {

    private PublishModel model;
    private ProgressDialog progressDialog;

    private Publish publish;
    private ArrayList<String> photos, findUrl;//图片链接
    private String storeId;//对应店铺的id
    private String publishId;//广告id
    private boolean isSave;//记录数据是否上传
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
        } else {
            data.publishTitle.set(publish.getTitle());
            data.content.set(publish.getContent());
            data.prizeCount.set(publish.getPrizeCount());
            publishId = publish.getObjectId();
            showStore(publish.getStoreId());
            showPhotos(publish.getPhotos(), publish.getFindUrls());
        }
        data.adapter.set(new RecyclerAdapter(R.layout.item_publish_image, data.photos));
        data.photos.add(getItem(null));
    }

    //显示店铺信息
    private void showStore(String storeId) {

    }

    //显示图片
    private void showPhotos(ArrayList<String> photos, ArrayList<String> findUrls) {
        for (int i = 0; i < photos.size(); i++) {
//        for (String url:photos) {
//            data.photos.add()
//        }
            deletePhoto(findUrls.get(i));
        }
    }

    //删除服务器上的图片
    private void deletePhoto(String findUrl) {
        model.deleteFile(findUrl, new BaseObserver<Map<String, String>>(null) {
            @Override
            protected void onSuccess(Map<String, String> map) {
                log(map.get("info"));
            }
        });
    }

    //获取图片列表项
    private PublishImageItem getItem(File file) {
        PublishImageItem item = new PublishImageItem(file);
        item.setListener(() -> {
            if (file == null) {//添加图片
                toSelect();
            } else {// 删除图片
                data.photos.remove(item);
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
                String path = SrcUtil.getPath(this, uri);//获取图片地址
                int position = data.photos.size() - 1;
                data.photos.add(position, getItem(new File(path)));
            }
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
        //判空操作
        if (data.money.get() == 0) {
            savePhotos();
        } else {

        }
    }

    //上传图片
    private void savePhotos() {
        progressDialog.showProgressDialog("请稍候", false);
        photos = new ArrayList<>();
        findUrl = new ArrayList<>();
        for (PublishImageItem item : data.photos) {
            File file = item.file.get();
            if (file != null) {
                model.uploadFile(preferencesUtil.getString(CustomKey.userId), file,
                        new BaseObserver<FileEntity>(null) {
                            @Override
                            protected void onSuccess(FileEntity fileEntity) {
                                photos.add(fileEntity.getLinkurl());
                                findUrl.add(fileEntity.getFindurl());
                                if (photos.size() == data.photos.size() - 1) {//图片上传完成
                                    commit();
                                }
                            }
                        });
            }
        }
    }

    //提交数据
    private void commit() {
        //避免重复提交
        synchronized (this) {
            if (!isSave) {
                isSave = true;
                if (publishId == null) {
                    savePublish();
                } else {
                    changePublish();
                }
            }
        }
    }

    //保存广告信息
    private void savePublish() {
        publish.setUserId(preferencesUtil.getString(CustomKey.userId));
        publish.setTitle(data.publishTitle.get());
        publish.setContent(data.content.get());
        publish.setStoreId(storeId);
        publish.setPrizeCount(data.prizeCount.get());
        publish.setPhotos(photos);
        publish.setFindUrls(findUrl);
        model.createPublish(publish, new BaseObserver<Publish>(progressDialog.dialog) {
            @Override
            protected void onSuccess(Publish publish) {
                activity.setResult(RESULT_OK);
                activity.finish();
            }
        });
    }

    //修改广告信息
    private void changePublish() {
        isSave = true;
        publish.clearSystemData();
        publish.setUserId(preferencesUtil.getString(CustomKey.userId));
        publish.setTitle(data.publishTitle.get());
        publish.setContent(data.content.get());
        publish.setStoreId(storeId);
        publish.setPrizeCount(data.prizeCount.get());
        publish.setPhotos(photos);
        model.changePublish(publishId, publish,
                new BaseObserver<Map<String, String>>(progressDialog.dialog) {
                    @Override
                    protected void onSuccess(Map<String, String> map) {
                        activity.setResult(RESULT_OK);
                        activity.finish();
                    }
                });
    }

}
