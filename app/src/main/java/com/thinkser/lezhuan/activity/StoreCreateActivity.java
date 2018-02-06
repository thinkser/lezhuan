package com.thinkser.lezhuan.activity;

import android.content.Intent;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.thinkser.core.base.BaseActivity;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.core.utils.LocationUtil;
import com.thinkser.core.view.CustomDialog;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.ActivityStoreCreateBinding;
import com.thinkser.lezhuan.dialog.HintDialog;
import com.thinkser.lezhuan.dialog.ProgressDialog;
import com.thinkser.lezhuan.entity.Store;
import com.thinkser.lezhuan.model.StoreModel;

import java.util.Map;

/**
 * 我的店铺
 */

public class StoreCreateActivity extends BaseActivity<AppData, ActivityStoreCreateBinding>
        implements AMapLocationListener {

    private StoreModel model;
    private LocationUtil locationUtil;
    private ProgressDialog progressDialog;

    private String id;//店铺id
    private Store store;
    private boolean isSave;

    @Override
    protected int getLayout() {
        return R.layout.activity_store_create;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData(Intent intent) {
        super.initData(intent);
        model = new StoreModel(this);
        progressDialog = new ProgressDialog(this);
        locationUtil = new LocationUtil();
        //接收从列表传过来的店铺信息
        store = (Store) intent.getSerializableExtra(CustomKey.info);
        if (store != null) {
            id = store.getObjectId();
            data.storeAddress.set(store.getStoreAddress());
            data.storeName.set(store.getStoreName());
            data.storePhone.set(store.getStorePhone());
        } else {
            store = new Store();
        }
        //启动定位
        locationUtil.getLocation(this, this);
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

    //点击保存按钮
    public void save() {
        if (data.storeName.get().equals("")) {
            toast(7015);
            return;
        }
        if (data.storePhone.get().equals("")) {
            toast(7000);
            return;
        }
        if (data.storePhone.get().length() < 11) {
            toast(7001);
            return;
        }
        if (data.storeAddress.get().equals("")) {
            toast(7016);
            return;
        }
        isSave = true;
        locationUtil.getLocation(this, this);
        progressDialog.showProgressDialog("请稍候", false);
        store.setUserId(preferencesUtil.getString(CustomKey.userId));
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            locationUtil.stopLocation();
            if (isSave) {
                store.setLatitude(aMapLocation.getLatitude());
                store.setLongitude(aMapLocation.getLongitude());
                store.setDistrict(aMapLocation.getDistrict());
                saveStoreInfo();
            } else {
                data.storeLocation.set(aMapLocation.getProvince() + " "
                        + aMapLocation.getCity() + " "
                        + aMapLocation.getDistrict());
            }
        } else {
            toast("定位失败");
            progressDialog.cancelProgressDialog();
        }
    }

    //将输入信息保存到实体类中
    private void saveStoreInfo() {
        store.setStoreName(data.storeName.get());
        store.setStorePhone(data.storePhone.get());
        store.setStoreAddress(data.storeAddress.get());
        store.clearSystemData();
        if (id == null) {//id不存在代表新建
            createStore();
        } else {//id存在代表修改
            changeStore();
        }
    }

    //创建店铺
    private void createStore() {
        model.createStore(store,
                new BaseObserver<Store>(progressDialog.dialog) {
                    @Override
                    protected void onSuccess(Store store) {
                        progressDialog.cancelProgressDialog();
                        activity.setResult(RESULT_OK);
                        activity.finish();
                    }
                });
    }

    //修改店铺
    private void changeStore() {
        model.changeStore(id, store,
                new BaseObserver<Map<String, String>>(progressDialog.dialog) {
                    @Override
                    protected void onSuccess(Map<String, String> map) {
                        progressDialog.cancelProgressDialog();
                        activity.setResult(RESULT_OK);
                        activity.finish();
                    }
                });
    }
}
