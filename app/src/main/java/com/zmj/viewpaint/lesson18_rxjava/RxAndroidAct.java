package com.zmj.viewpaint.lesson18_rxjava;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zmj.viewpaint.R;
import com.zmj.viewpaint.lesson18_rxjava.model.BaseResp;
import com.zmj.viewpaint.lesson18_rxjava.model.Data;
import com.zmj.viewpaint.lesson18_rxjava.model.DetailResp;
import com.zmj.viewpaint.lesson18_rxjava.model.Repo;
import com.zmj.viewpaint.lesson18_rxjava.net.Api;
import com.zmj.viewpaint.lesson18_rxjava.net.GongShangApi;
import com.zmj.viewpaint.lesson18_rxjava.net.ServiceCreator;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * GitHub : https://github.com/ZmjDns
 * Time : 2020/7/13
 * Description :
 */
public class RxAndroidAct extends AppCompatActivity {

    private TextView text;

    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_lesson18_rxjava);

        text = findViewById(R.id.text);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        /*api.getRepos("ZmjDns")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Repo>>() {
            @Override               //参数取消
            public void onSubscribe(Disposable d) {//此方法运行的线程跟当前线程（主线程）一样，主要取决于该方法所在的线程
                disposable = d; //保存下来，用于取消事件
                text.setText("正在请求...");
            }

            @Override
            public void onSuccess(List<Repo> repos) {
                text.setText(repos.get(0).getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.i("RxAndroidAct","返回错误：" + e.getMessage());
            }
        });*/

        Single.just("1")
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        text.setText(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        Single<String> single = Single.just("1");
        single = single.subscribeOn(Schedulers.io());
        single = single.subscribeOn(AndroidSchedulers.mainThread());
        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });

        Single.just(1)
                .map(new Function<Integer, String>() {
                    @Override  //将要发送的的数据转换成需要的数据
                    public String apply(Integer integer) throws Exception {
                        return String.valueOf(integer);
                    }
                })
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        getBaseData();

    }


    private void getBaseData(){
        Data data = new Data("天津君腾跃飞工程有限公司");   //天津天空地海科技有限公司  //天津君腾跃飞工程有限公司  //天津云科世讯科技
        final GongShangApi gongShangApi = ServiceCreator.INSTANCE.create(GongShangApi.class);

        gongShangApi.getBaseInfo(data)
                .enqueue(new Callback<BaseResp>() {
                    @Override
                    public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                        if (response.isSuccessful()){
                            final BaseResp resp = response.body();
                            Log.d("RxAndroidAct",resp.getData().getSearchword());

                            List<BaseResp.Result.Data> data1 = resp.getData().getResult().getData();
                            if (data1.size() > 0){
                                BaseResp.Result.Data baseData = data1.get(0);
                                String pripId = baseData.getPripid();

                                String page = "corp-query-entprise-info-primaryinfoapp-entbaseInfo-" + pripId + ".html";
                                int nodeNum = baseData.getNodeNum();
                                int entType = baseData.getEntType();
                                gongShangApi.getDetailInfo(page,nodeNum,entType,"A")
                                        .enqueue(new Callback<DetailResp>() {
                                            @Override
                                            public void onResponse(Call<DetailResp> call, Response<DetailResp> response) {
                                                if (response.isSuccessful()){
                                                    DetailResp detailResp = response.body();
                                                    DetailResp.Result result = detailResp.getResult();

                                                    Log.d("RxAndroidAct",result.getEntName());
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<DetailResp> call, Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResp> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        //disposable.dispose();
        super.onDestroy();
    }
}
