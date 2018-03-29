package com.pal.collecttool.net;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Admin on 2018/3/28.
 */

public class OkHttpRequest {
    private static final String TAG = OkHttpRequest.class.getSimpleName();

    public void getDatasync(final String url ,Response response){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(url)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        Log.d(TAG,"response.code()=="+response.code());
                        Log.d(TAG,"response.message()=="+response.message());
                        Log.d(TAG,"res=="+response.body().string());
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * get的异步请求
     * */
    private void getDataAsync(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    Log.d("kwwl","获取数据成功了");
                    Log.d("kwwl","response.code()=="+response.code());
                    Log.d("kwwl","response.body().string()=="+response.body().string());
                }
            }
        });
    }

    /**
     * Post请求也分同步和异步两种方式，同步与异步的区别和get方法类似，所以此时只讲解post异步请求的使用方法。
     * */
    private void postDataWithParame(String url) {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("username","zhangsan");//传递键值对参数
        Request request = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(formBody.build())//传递请求体
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });//回调方法的使用与get异步请求相同，此时略。
    }

    /**
     * 使用RequestBody传递Json或File对象
     * RequestBody是抽象类，故不能直接使用，但是他有静态方法create，使用这个方法可以得到RequestBody对象。
     * 这种方式可以上传Json对象或File对象。
     *
     * 上传json对象
     * */
    public void uploadJSON(String url,String jsonStr){
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /* *
    上传json对象
     * */
    public void uploadFile(String url,String filePath){
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType fileType = MediaType.parse("File/*");//数据类型为json格式，
        File file = new File(filePath);//file对象.
        RequestBody body = RequestBody.create(fileType , file );
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 使用MultipartBody同时传递键值对参数和File对象
     * */
    public void uploadMultipartBody(String url,String filePath,String jsonStr){
        OkHttpClient client = new OkHttpClient();
        File file = new File(filePath);//file对象.
        MultipartBody multipartBody =new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("groupId",""+jsonStr)//添加键值对参数
                .addFormDataPart("title","title")
                .addFormDataPart("file",file.getName(),RequestBody.create(MediaType.parse("file/*"), file))//添加文件
                .build();
        final Request request = new Request.Builder()
                .url(URLContant.CHAT_ROOM_SUBJECT_IMAGE)
                .post(multipartBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
