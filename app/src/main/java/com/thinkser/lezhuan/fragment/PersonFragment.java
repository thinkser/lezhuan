package com.thinkser.lezhuan.fragment;

import android.content.Intent;
import android.widget.Toast;

import com.thinkser.core.base.BaseFragment;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.core.utils.SrcUtil;
import com.thinkser.core.view.CustomDialog;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.activity.BeginActivity;
import com.thinkser.lezhuan.activity.CardActivity;
import com.thinkser.lezhuan.activity.CollectActivity;
import com.thinkser.lezhuan.activity.PublishActivity;
import com.thinkser.lezhuan.activity.SettingActivity;
import com.thinkser.lezhuan.activity.StoreActivity;
import com.thinkser.lezhuan.activity.TransmitActivity;
import com.thinkser.lezhuan.activity.WalletActivity;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
import com.thinkser.lezhuan.databinding.FragmentPersonBinding;
import com.thinkser.lezhuan.dialog.HintDialog;
import com.thinkser.lezhuan.entity.Customer;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 个人中心
 */

public class PersonFragment extends BaseFragment<AppData, FragmentPersonBinding> {

    @Override
    protected int getLayout() {
        return R.layout.fragment_person;
    }

    @Override
    protected AppData getData() {
        return new AppData();
    }

    @Override
    protected void initData() {
        super.initData();
        Observable.fromArray(this.getContext().getCacheDir().getAbsolutePath())
                .map(SrcUtil::getSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(null) {
                    @Override
                    protected void onSuccess(String s) {
                        data.cacheSize.set(s);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        showPerson();
    }

    //显示用户信息
    private void showPerson() {
        data.username.set(preferencesUtil.getString(CustomKey.username));
        data.portrait.set(preferencesUtil.getString(CustomKey.portrait));
        data.signature.set(preferencesUtil.getString(CustomKey.signature));
    }

    public void toSetting() {
        skip(SettingActivity.class);
    }

    public void toPublish() {
        skip(PublishActivity.class);
    }

    public void toTransmit() {
        skip(TransmitActivity.class);
    }

    public void toCollect() {
        skip(CollectActivity.class);
    }

    public void toStore() {
        Intent intent = new Intent(activity, StoreActivity.class);
        intent.putExtra("titleText", "我的店铺");
        startActivity(intent);
    }

    public void toWallet() {
        skip(WalletActivity.class);
    }

    public void toCard() {
        skip(CardActivity.class);
    }

    public void toAbout() {

    }

    public void toHelp() {

    }

    public void toFeedback() {

    }

    public void toJoin() {

    }

    public void clear() {
        SrcUtil.deleteFiles(new File(getContext().getCacheDir().getAbsolutePath()));
        data.cacheSize.set(SrcUtil.getSize(this.getContext().getCacheDir().getAbsolutePath()));
        toast("缓存已清除");
    }

    public void exit() {
        new HintDialog(getActivity(), "确定要退出吗？")
                .setCancelable(true)
                .setRightButton("退 出", R.color.colorTheme)
                .setOnHintClickListener(new HintDialog.OnClickListener() {
                    @Override
                    public void onLeftClick(CustomDialog dialog) {

                    }

                    @Override
                    public void onRightClick(CustomDialog dialog) {
                        Customer customer = new Customer();
                        customer.logout(getActivity());
                        skip(BeginActivity.class);
                        getActivity().finish();
                    }
                });
    }
}
