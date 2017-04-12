package com.letv.xutils;

import android.util.ArrayMap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.annotation.HttpResponse;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghuancheng on 17-4-7.
 */
public class BaseResponseParser implements ResponseParser {
    private static final String TAG = "zhc";
    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        //resultType有可能的情况：BaseResponse<List<MyClass>>、BaseResponse<String>、BaseResponse<MyClass>
        //result为网络返回的xml、json数据
        //data;errno;errmsg
        //该方法目的解析xml、json数据
        //TODO 实现解析方法
        Map resultMap = new ArrayMap();
        resultMap.put("data", "I am data");
        resultMap.put("errno", "10000");
        resultMap.put("errmsg", "I am msg");
        JSONObject jsonObject = new JSONObject(resultMap);
        result = jsonObject.toString();
        Log.d(TAG, "parse: " + result);

        //getActualTypeArguments()得到泛型参数Type数组，如BaseResponse<MyClass1，MyClass2>，得到的就是[MyClass1, MyClass2]
        Type dataType = ((ParameterizedType) resultType).getActualTypeArguments()[0];

        BaseResponse rspObj = new BaseResponse();
        //首先解析数据到BaseResponse对象,data、errno、errmsg；
        String data = getResponseData(result, rspObj);
        if (data != null) {
            Type rawType = null;
            try {
                //获得此类型的类型
                rawType = ((ParameterizedType) dataType).getRawType();
            } catch (Exception e) {
//                LogHelper.w("[parse]  catch exception getMessage=" +
//                        e.getMessage() + " toString()=" + e.toString());
            }


            if (rawType == List.class) {
                //如果时List类型，则解析List的泛型参数的Type，如Base<List<MyClass>>，则解析出MyClass
                Type cType = ((ParameterizedType) dataType).getActualTypeArguments()[0];

                rspObj.data = ParseHelper.parseArrayByGson(data, cType);

            } else {
                if (dataType == String.class) {
                    rspObj.data = data;
                } else {
                    //BaseResponse<MyClass>，泛型参数为MyClass，解析出MyClass的对象
                    rspObj.data = ParseHelper.parseByGson(data, dataType);
                }
            }
        }
        return rspObj;
    }

    protected static String getResponseData(String respond, BaseResponse rsp) throws JSONException {
        //先解析第一层数据，把Json第一层数据拿到
        //通过String构造JSONObject
        JSONObject jObj = new JSONObject(respond);
        //通过JSONObjec得到对应标签的值
        rsp.errno = jObj.getInt("errno");
        if (!jObj.isNull("errmsg")) {
            rsp.errmsg = jObj.getString("errmsg");
        }
        if (rsp.errno != 10000) {
        }

        if (!jObj.isNull("data")) {
            return jObj.getString("data");
        }
        return null;
    }
}
