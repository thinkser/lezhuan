package com.thinkser.lezhuan.model;

import android.app.Activity;

import com.thinkser.core.base.BaseModel;
import com.thinkser.core.base.BaseObserver;
import com.thinkser.lezhuan.api.FriendAPI;
import com.thinkser.lezhuan.api.MainAPI;
import com.thinkser.lezhuan.entity.Friend;
import com.thinkser.lezhuan.entity.Publish;
import com.thinkser.lezhuan.entity.Transmit;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * 首页、好友、个人中心的model。
 */

public class MainModel extends BaseModel {

    public MainModel(Activity activity) {
        super(activity);
    }

    public void getTransmit(String id, BaseObserver<Publish> baseObserver) {
        String where = "{\"receiverId\":\"" + id + "\"}";
        getInstance().getTransmit(Transmit.class.getSimpleName(), where)
                .map(new Function<Map<String, List<Transmit>>, List<Transmit>>() {
                    @Override
                    public List<Transmit> apply(Map<String, List<Transmit>> map) throws Exception {
                        return map.get("results");
                    }
                })
                .filter(new Predicate<List<Transmit>>() {
                    @Override
                    public boolean test(List<Transmit> transmits) throws Exception {
                        return transmits != null && transmits.size() > 0;
                    }
                })
                .flatMap(new Function<List<Transmit>, ObservableSource<Transmit>>() {
                    @Override
                    public ObservableSource<Transmit> apply(List<Transmit> transmits) throws Exception {
                        return Observable.fromIterable(transmits);
                    }
                })
                .flatMap(new Function<Transmit, ObservableSource<Publish>>() {
                    @Override
                    public ObservableSource<Publish> apply(Transmit transmit) throws Exception {
                        return getInstance().
                                getTransmitInfo(Publish.class.getSimpleName(), transmit.getPublishId());
                    }
                })
                .compose(netUtil.compose())
                .subscribe(baseObserver);

    }

    public void searchPublish(String keyword, BaseObserver<Publish> baseObserver) {
        String where = "{\"title\":\"" + keyword + "\"}";
        netUtil.getInstance(headers, MainAPI.class)
                .search(Publish.class.getSimpleName(), where)
                .map(new Function<Map<String, List<Publish>>, List<Publish>>() {
                    @Override
                    public List<Publish> apply(Map<String, List<Publish>> map) throws Exception {
                        return map.get("results");
                    }
                })
                .filter(new Predicate<List<Publish>>() {
                    @Override
                    public boolean test(List<Publish> publishes) throws Exception {
                        return publishes != null && publishes.size() > 0;
                    }
                })
                .flatMap(new Function<List<Publish>, ObservableSource<Publish>>() {
                    @Override
                    public ObservableSource<Publish> apply(List<Publish> publishes) throws Exception {
                        return Observable.fromIterable(publishes);
                    }
                })
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    public void transmit(String userId, String publishId, BaseObserver<Transmit> baseObserver) {
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
                .flatMap(new Function<List<Friend>, ObservableSource<Friend>>() {
                    @Override
                    public ObservableSource<Friend> apply(List<Friend> friends) throws Exception {
                        return Observable.fromIterable(friends);
                    }
                })
                .flatMap(new Function<Friend, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Friend friend) throws Exception {
                        return Observable.fromArray(friend.getUserId(), friend.getFriendId());
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !(s.equals(userId));
                    }
                })
                .flatMap(new Function<String, ObservableSource<Transmit>>() {
                    @Override
                    public ObservableSource<Transmit> apply(String s) throws Exception {
                        Transmit transmit = new Transmit();
                        transmit.setPublishId(publishId);
                        transmit.setReceiverId(s);
                        transmit.setTransmitId(userId);
                        return getInstance()
                                .transmit(Transmit.class.getSimpleName(), getBody(transmit));
                    }
                })
                .compose(netUtil.compose())
                .subscribe(baseObserver);
    }

    private MainAPI getInstance() {
        return netUtil.getInstance(headers, MainAPI.class);
    }

}
