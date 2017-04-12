package com.letv.xutils;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;
//Test
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zhc";
    private static String URL = "http://www.baidu.com/img/bd_logo1.png";
    private static String GET_URL = "https://www.baidu.com";

    private ImageView mImageView_1;
    private ImageOptions mImageOptions;
    private ImageView mImageView_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageOptions = new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                .setCircular(true).build();

        //xutils 加载网络Image方法1
        mImageView_1 = (ImageView) findViewById(R.id.imageView_1);
        x.image().bind(mImageView_1, URL, mImageOptions);

        //xutils 加载网络Image方法2，可以得到Drawable对象
        mImageView_2 = (ImageView) findViewById(R.id.imageView_2);
        x.image().loadDrawable(URL, mImageOptions, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                mImageView_2.setImageDrawable(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        //请求网络
        BaseRequestParams requestParams = new BaseRequestParams(GET_URL);
        //BaseResponse<T>中的T表明data的数据类型，List、String、或其他
        //get本身为异步操作，需要传入回调
        x.http().get(requestParams, new Callback.CommonCallback<BaseResponse<String>>() {

            @Override
            public void onSuccess(BaseResponse result) {
                Log.d(TAG, "onSuccess: " +result.data);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(TAG, "onCancelled: ");
            }

            @Override
            public void onFinished() {
                Log.d(TAG, "onFinished: ");
            }
        });
    }
}
