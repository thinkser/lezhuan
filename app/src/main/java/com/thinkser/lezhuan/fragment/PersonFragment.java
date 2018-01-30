package com.thinkser.lezhuan.fragment;

import com.thinkser.core.base.BaseFragment;
import com.thinkser.core.utils.PreferencesUtil;
import com.thinkser.lezhuan.R;
import com.thinkser.lezhuan.activity.BeginActivity;
import com.thinkser.lezhuan.activity.PublishActivity;
import com.thinkser.lezhuan.activity.StoreActivity;
import com.thinkser.lezhuan.data.AppData;
import com.thinkser.lezhuan.data.CustomKey;
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
        showPerson();
    }

    //显示用户信息
    private void showPerson() {
        PreferencesUtil util = new PreferencesUtil(getActivity());
        data.username.set(util.getString(CustomKey.username));
        data.portrait.set(util.getString(CustomKey.portrait));
        data.signature.set(util.getString(CustomKey.signature));
    }

    public void toSetting() {

    }

    public void toPublish() {
        skip(PublishActivity.class);
    }

    public void toTransmit() {

    }

    public void toCollect() {

    }

    public void toStore() {
        skip(StoreActivity.class);
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
                        skip(BeginActivity.class);
                        getActivity().finish();
                    }
                });
    }


}
