package com.thinkser.lezhuan.fragment;

import android.content.Intent;

import com.thinkser.core.base.BaseFragment;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.activity.BeginActivity;
import com.thinkser.lezhuan.activity.PublishActivity;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.databinding.FragmentPersonBinding;
import com.thinkser.lezhuan.dialog.HintDialog;
import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.model.MainModel;

/**
 * 个人中心
 */

public class PersonFragment extends BaseFragment<AppData, FragmentPersonBinding> {

    private MainModel model;
    private HintDialog hintDialog;

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
        model = new MainModel(getActivity());
        hintDialog = new HintDialog(getActivity());
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
        hintDialog.showHintDialog("确定要退出吗？", true,
                (dialog, view) -> {
                    if (view.getId() == R.id.tv_hint_ensure) {
                        Customer customer = new Customer();
                        customer.logout(getActivity());
                        statActivity(BeginActivity.class);
                        getActivity().finish();
                    }
                });
    }

    private void statActivity(Class activity) {
        getActivity().startActivity(new Intent(getActivity(), activity));
    }
}
