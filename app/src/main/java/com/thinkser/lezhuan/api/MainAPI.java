package com.thinkser.lezhuan.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MainAPI {

    @GET("https://api.bmob.cn/1/classes/Customer/ff55c06aa2")
    Call<ResponseBody> getPerson();
}
