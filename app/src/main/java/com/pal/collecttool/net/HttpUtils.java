package com.pal.collecttool.net;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by liupaipai on 2018/3/29.
 *
 * 项目暂时只需要
 *     向服务器发送一个文件和一个文件类型即可
 *
 *     用OKHTTP3实现
 *
 */

public class HttpUtils {
    private static final String TAG = HttpUtils.class.getSimpleName();

    private static HttpUtils mInstance;
    private OkHttpClient mOkHttpClient;

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    private HttpUtils()
    {
        mOkHttpClient = new OkHttpClient();
    }

    public static HttpUtils getInstance()
    {
        if (mInstance == null)
        {
            synchronized (HttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new HttpUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * Post方式提交分块请求
     * MultipartBody 可以构建复杂的请求体，与HTML文件上传形式兼容。
     * 多块请求体中每块请求都是一个请求体，可以定义自己的请求头。
     * 这些请求头可以用来描述这块请求，例如他的Content-Disposition。
     * 如果Content-Length和Content-Type可用的话，他们会被自动添加到请求头中。
     * */

}
