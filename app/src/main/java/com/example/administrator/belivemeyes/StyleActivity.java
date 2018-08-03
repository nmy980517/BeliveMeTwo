package com.example.administrator.belivemeyes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.administrator.belivemeyes.Adapter.VideoAdapter;
import com.example.administrator.belivemeyes.Bean.JavaBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class StyleActivity extends AppCompatActivity {

    private String url = "http://qf.56.com/home/v4/moreAnchor.android?&type=0&index=0";
    private RecyclerView mRv;
    private List<JavaBean.MessageBean.AnchorsBean> lists = new ArrayList<>();
    private VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);

        initView();

        //okHttp解析数据
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(url).build();//拿到地址
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //用不到 无所谓
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //重要方法
                ResponseBody body = response.body();
                byte [] bytes = body.bytes();
                String result = new String(bytes);
                Gson gson = new Gson();
                JavaBean javaBean = gson.fromJson(result, JavaBean.class);
                lists.addAll(javaBean.getMessage().getAnchors());

                runOnUiThread(new Runnable() {//切换到主线程 去设置适配器
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        adapter = new VideoAdapter(this, lists);
        mRv.setAdapter(adapter);
    }

    //加载框架
    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.style_rv);

        //各种适配器设置
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);//三列 垂直
        mRv.setLayoutManager(staggeredGridLayoutManager);
    }
}
