package com.thinkser.core.utils;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;

/**
 * bmob工具类
 */

public class BmobUtil<E extends BmobObject> {

    private BmobQuery<E> query;

    public BmobUtil() {
        query = new BmobQuery<>();
    }

    //设置查询条件
    public BmobUtil<E> query(String key, String value) {
        query.addWhereEqualTo(key, value);
        return this;
    }

    //设置返回数据，如果不加上这条语句，默认返回10条数据
    public BmobUtil<E> setLimit(int num) {
        query.setLimit(num);
        return this;
    }

    //查询一条数据
    public void getObject(String id, QueryListener<E> listener) {
        query.getObject(id, new cn.bmob.v3.listener.QueryListener<E>() {
            @Override
            public void done(E e, BmobException ex) {
                if (ex == null)
                    listener.success(e);
                else
                    listener.failed(ex);
            }
        });
    }

    //查询多条数据
    public void findObjects(FindListener<E> listener) {
        query.findObjects(new cn.bmob.v3.listener.FindListener<E>() {
            @Override
            public void done(List<E> list, BmobException e) {
                if (e == null)
                    listener.success(list);
                else
                    listener.failed(e);
            }
        });
    }

    //更新数据
    public void update(E e, UpdateListener listener) {
        e.update(e.getObjectId(), new cn.bmob.v3.listener.UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null)
                    listener.success();
                else
                    listener.failed(e);
            }
        });
    }

    //获取验证码
    public void sendCode(String phone, QueryListener<Integer> listener) {
        BmobSMS.requestSMSCode(phone, "验证码",
                new cn.bmob.v3.listener.QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null)
                            listener.success(integer);
                        else
                            listener.failed(e);

                    }
                });
    }

    //验证验证码
    public void verifyCode(String phone, String code, VerifyListener listener) {
        BmobSMS.verifySmsCode(phone, code, new cn.bmob.v3.listener.UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null)
                    listener.success();
                else
                    listener.failed(e);
            }
        });
    }

    public interface QueryListener<T> {
        void success(T t);

        void failed(BmobException e);
    }

    public interface FindListener<T> {
        void success(List<T> list);

        void failed(BmobException e);
    }

    public interface UpdateListener {
        void success();

        void failed(BmobException e);
    }

    public interface VerifyListener {
        void success();

        void failed(BmobException e);
    }

}
