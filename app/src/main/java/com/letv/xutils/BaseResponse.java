package com.letv.xutils;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by zhanghuancheng on 17-4-7.
 */

@HttpResponse(parser = BaseResponseParser.class)
//使用parser指定的解析类去解析返回的xml、json数据，然后丢给BaseResponse

class BaseResponse<T> {
    int errno;
    String errmsg;
    T data;
}
