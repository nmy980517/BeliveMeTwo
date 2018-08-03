package com.example.administrator.belivemeyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.belivemeyes.Bean.LoadingBean;
import com.example.administrator.belivemeyes.Bean.PlayBean;
import com.google.gson.Gson;

import java.io.IOException;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PlayActivity extends AppCompatActivity {

    private String playBaseURL = "http://qf.56.com/play/v1/preLoading.android?roomId=";//直接接口
    private JZVideoPlayerStandard mJzPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initView();
        Intent intent = getIntent();
        String roomid = intent.getStringExtra("roomid");
        playBaseURL = playBaseURL+roomid;//拼接链接

        //okHttp解析
        final OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(playBaseURL).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //没吊用
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    //重点解析方法
                ResponseBody body = response.body();
                final byte[] bytes = body.bytes();
                String result = new String(bytes);
                Gson gson = new Gson();
                LoadingBean loadingBean = gson.fromJson(result, LoadingBean.class);
                LoadingBean.MessageBean message = loadingBean.getMessage();
                String rUrl = message.getRUrl();

                OkHttpClient okHttp = new OkHttpClient();
                Request request2 = new Request.Builder().get().url(rUrl).build();
                okHttpClient.newCall(request2).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //没屁用
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        ResponseBody responseBody = response.body();
                        byte[] bytes1 = responseBody.bytes();
                        String result2 = new String(bytes1);
                        Gson gson1 = new Gson();
                        PlayBean playBean = gson1.fromJson(result2, PlayBean.class);
                        final String playUrl = playBean.getUrl();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mJzPlay.setUp(playUrl, JZVideoPlayer.SCREEN_LAYOUT_LIST,"直播");
                            }
                        });
                    }
                });
            }
        });
    }

    //加载控件
    private void initView() {
        mJzPlay = (JZVideoPlayerStandard) findViewById(R.id.jzPlay);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mJzPlay.clearAnimation();
    }
}
