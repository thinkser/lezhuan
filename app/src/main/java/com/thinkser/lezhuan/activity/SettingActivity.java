package com.thinkser.lezhuan.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;

import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.core.utils.GlideEngine;
import com.thinkser.core.utils.SrcUtil;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivitySettingBinding;
import com.thinkser.lezhuan.dialog.ProgressDialog;
import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.entity.FileEntity;
import com.thinkser.lezhuan.model.BeginModel;
import com.thinkser.lezhuan.model.PublishModel;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 设置界面
 */

public class SettingActivity extends BaseActivity<AppData, ActivitySettingBinding> {

    private BeginModel beginModel;
    private PublishModel publishModel;

    private String portrait;
    private ProgressDialog progressDialog;
    private final int REQUEST_SELECT_IMAGE = 1;

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        beginModel = new BeginModel(this);
        publishModel = new PublishModel(this);
        progressDialog = new ProgressDialog(this);
    }

    //跳转到图片选择界面
    public void changePortrait() {
        Matisse.from(activity)
                .choose(MimeType.allOf()) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(1) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_SELECT_IMAGE); // 设置作为标记的请求码
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //图片选择器返回结果
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
            progressDialog.showProgressDialog("请稍候", false);
            List<Uri> selected = Matisse.obtainResult(intent);
            portrait = SrcUtil.getPath(this, selected.get(0));//获取图片地址
            deletePhotos();
        }
    }

    //删除服务器上的图片
    private void deletePhotos() {
        if (preferencesUtil.getString(CustomKey.findUrls).equals("")) {
            savePhotos();
            return;
        }
        publishModel.deleteFile(preferencesUtil.getString(CustomKey.findUrls),
                new BaseObserver<Map<String, String>>(null) {
                    @Override
                    protected void onSuccess(Map<String, String> map) {
                        log(map.get("info"));
                        savePhotos();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        savePhotos();
                    }
                });
    }

    //上传图片
    private void savePhotos() {
        File file = new File(portrait);
        publishModel.uploadFile(preferencesUtil.getString(CustomKey.userId), file,
                new BaseObserver<FileEntity>(null) {
                    @Override
                    protected void onSuccess(FileEntity fileEntity) {
                        saveUser(fileEntity);
                    }
                });
    }

    private void saveUser(FileEntity fileEntity) {
        //本地保存
        preferencesUtil.setString(CustomKey.portrait, fileEntity.getLinkurl())
                .setString(CustomKey.findUrls, fileEntity.getFindurl())
                .save();
        Customer customer = new Customer();
        customer.setFindUrl(fileEntity.getFindurl());
        customer.setPortrait(fileEntity.getLinkurl());
        customer.setSignature(null);
        customer.clearSystemData();
        beginModel.forget(preferencesUtil.getString(CustomKey.userId), customer,
                new BaseObserver<Map<String, String>>(progressDialog.dialog) {
                    @Override
                    protected void onSuccess(Map<String, String> map) {
                        toast("更换成功");
                    }
                });
    }
}
