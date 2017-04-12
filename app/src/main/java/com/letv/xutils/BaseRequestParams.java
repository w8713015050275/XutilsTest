package com.letv.xutils;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.ParamsBuilder;

/**
 * Created by zhanghuancheng on 17-4-10.
 */

public class BaseRequestParams extends RequestParams {
    public BaseRequestParams() {
    }

    public BaseRequestParams(String uri) {
        super(uri);
    }

    public BaseRequestParams(String uri, ParamsBuilder builder, String[] signs, String[] cacheKeys) {
        super(uri, builder, signs, cacheKeys);
    }

}
