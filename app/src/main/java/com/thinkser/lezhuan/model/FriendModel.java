package com.thinkser.lezhuan.model;

import android.app.Activity;

import com.google.gson.Gson;
import com.thinkser.core.base.BaseModel;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.api.FriendAPI;
import com.thinkser.lezhuan.entity.Customer;
import com.thinkser.lezhuan.entity.Friend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * 好友相关model
 */

public class FriendModel extends BaseModel {

    public FriendModel(Activity activity) {
        super(activity);
    }

    public void getFriend(String userId, BaseObserver<Friend> baseObserver) {
        String where = "{\"$or\":[{\"userId\":\"" + userId +
                "\"},{\"friendId\":\"" + userId + "\"}]}";
        netUtil.getInstance(headers, FriendAPI.class)
                .getFriend(Friend.class.getSimpleName(), where)
                .map(new Function<Map<String, List<Friend>>, List<Friend>>() {
                    @Override
                    public List<Friend> apply(Map<String, List<Friend>> map) throws Exception {
                        return map.get("results");
                    }
                })
                .filter(new Predicate<List<Friend>>() {
                    @Override
                    public boolean test(List<Friend> friends) throws Exception {
                        return friends != null && friends.size() > 0;
                    }
                })
                .flatMap(new Function<List<Friend>, ObservableSource<Friend>>() {
                    @Override
                    public ObservableSource<Friend> apply(List<Friend> friends) throws Exception {
                        return Observable.fromIterable(friends);
                    }
                })
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void getNewFriend(String userId, BaseObserver<Friend> baseObserver) {
        String where = "{\"$or\":[{\"userId\":\"" + userId +
                "\"},{\"friendId\":\"" + userId + "\"}]}";
        netUtil.getInstance(headers, FriendAPI.class)
                .getFriend(Friend.class.getSimpleName(), where)
                .map(new Function<Map<String, List<Friend>>, List<Friend>>() {
                    @Override
                    public List<Friend> apply(Map<String, List<Friend>> map) throws Exception {
                        return map.get("results");
                    }
                })
                .filter(new Predicate<List<Friend>>() {
                    @Override
                    public boolean test(List<Friend> friends) throws Exception {
                        return friends != null && friends.size() > 0;
                    }
                })
                .flatMap(new Function<List<Friend>, ObservableSource<Friend>>() {
                    @Override
                    public ObservableSource<Friend> apply(List<Friend> friends) throws Exception {
                        return Observable.fromIterable(friends);
                    }
                })
                .filter(new Predicate<Friend>() {
                    @Override
                    public boolean test(Friend friend) throws Exception {
                        return friend.getState() == 1;
                    }
                })
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void search(String keyWord, BaseObserver<Customer> baseObserver) {
        String where = "{\"$or\":[{\"phone\":\"" + keyWord +
                "\"},{\"username\":\"" + keyWord + "\"}]}";
        netUtil.getInstance(headers, FriendAPI.class)
                .search(Customer.class.getSimpleName(), where)
                .map(new Function<Map<String, List<Customer>>, List<Customer>>() {
                    @Override
                    public List<Customer> apply(Map<String, List<Customer>> map) throws Exception {
                        return map.get("results");
                    }
                })
                .filter(new Predicate<List<Customer>>() {
                    @Override
                    public boolean test(List<Customer> list) throws Exception {
                        return list != null && list.size() > 0;
                    }
                })
                .map(new Function<List<Customer>, Customer>() {
                    @Override
                    public Customer apply(List<Customer> list) throws Exception {
                        return list.get(0);
                    }
                })
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void addFriend(Friend friend, BaseObserver<Friend> baseObserver) {
        netUtil.getInstance(headers, FriendAPI.class)
                .addFriend(Friend.class.getSimpleName(), getBody(friend))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void agree(String id, HashMap map, BaseObserver<Map<String, String>> baseObserver) {
        netUtil.getInstance(headers, FriendAPI.class)
                .agree(Friend.class.getSimpleName(), id, getBody(map))
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }
}
