package com.thinkser.lezhuan.fragment;

import android.content.Intent;

import com.thinkser.core.base.BaseFragment;
import com.thinkser.core.utils.BmobUtil;
import com.thinkser.core.utils.PreferencesUtil;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.activity.BeginActivity;
import com.thinkser.lezhuan.activity.PublishActivity;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.PreferenceKey;
import com.thinkser.lezhuan.databinding.FragmentPersonBinding;
import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.model.MainModel;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * 个人中心
 */

public class PersonFragment extends BaseFragment<AppData, FragmentPersonBinding> {

    private MainModel model;

    @Override
    protected int getLayout() {
        return R.layout.fragment_person;
    }

    @Override
    protected AppData getData() {
        return AppData.getAppData();
    }

    @Override
    protected void initData() {
        model = new MainModel(getActivity());
        String token = new PreferencesUtil(getActivity()).getString(PreferenceKey.token);
        model.getPerson(token, new BmobUtil.QueryListener<Customer>() {
            @Override
            public void success(Customer customer) {

            }

            @Override
            public void failed(BmobException e) {

            }
        });

        data.username.set("thinkser");
    }

    public void toSetting() {

    }

    public void toPublish() {
        statActivity(PublishActivity.class);
    }

    public void toTransmit() {

    }

    public void toCollect() {

    }

    public void toStore() {

    }

    public void toWallet() {

    }

    public void toCard() {

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
        toast("缓存已清除");
    }

    public void exit() {
        Customer customer = new Customer();
        customer.logout(getActivity());
        AppData.clear();
        getActivity().startActivity(new Intent(getActivity(), BeginActivity.class));
        getActivity().finish();
    }

    private void statActivity(Class activity) {
        getActivity().startActivity(new Intent(getActivity(), activity));
    }
}
