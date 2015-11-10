package com.zqds.client.http.parser;

import java.io.IOException;

import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.zqds.client.http.listener.IResultDataParser;
import com.zqds.client.model.AbstractBaseModel;

/**
 * 数据解析器工厂
 * 
 * @author xiangyutian <br/>
 *         create at 2014-3-21 下午6:14:59
 */
public class JsonParserFactory {
	
    /**
     * TAG
     */
    private static final String TAG = "JsonParserFactory";
   
    public static <T extends AbstractBaseModel> T parseStringJson(Class<T> cls, Object context) throws JSONException,
           IOException, Exception {
        final T response;
        try {
            response = new Gson().fromJson((String) context, cls);
        } catch (JsonSyntaxException e) {
            throw new JSONException(e.getMessage());
        } catch (JsonIOException e) {
            throw new IOException(e.getMessage());
        } catch (JsonParseException e) {
            throw new JSONException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JSONException(e.getMessage());
        } catch (Exception e){
           throw new Exception(e.getMessage());	
        }
        if (response == null) {
            throw new JSONException(TAG + " JsonParser is null.");
        }
//        if (SUC_TAG != response.getState()) {
//            throw new QdocApiException(response.getState(), response.getErrorMsg());
//        }
        return response;
    }

    /**
     * 解析基本数据类型
     * 
     * @param cls 
     * @return
     */
    public static <T extends AbstractBaseModel> IResultDataParser<T> parseBaseModel(final Class<T> cls) {
        return new IResultDataParser<T>() {
            @Override
            public T parse(Object response) throws JSONException, IOException, Exception {
                return parseStringJson(cls, (String) response);
            }
        };
    }
    
    /**
     * 解析json字符串
     * @param cls：实体类
     * @param str：字符串
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static <T> T parseJson(Class<T> cls, String str) throws JSONException,
           IOException {
        final T response;
        try {
            response = new Gson().fromJson((String) str, cls);
        } catch (JsonSyntaxException e) {
            throw new JSONException(e.getMessage());
        } catch (JsonIOException e) {
            throw new IOException(e.getMessage());
        } catch (JsonParseException e) {
            throw new JSONException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new JSONException(e.getMessage());
        }
        if (response == null) {
            throw new JSONException(TAG + " JsonParser is null.");
        }
        return response;
    }
}
