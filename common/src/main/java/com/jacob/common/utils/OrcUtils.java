package com.jacob.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.jacob.common.model.base.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * 百度OCR工具类
 */
@Slf4j
@Component
public class OrcUtils {

    private static final String API_KEY = "tvhDL47Q6B5JxTF1RydN2R5g";
    private static final String SECRET_KEY = "8gIeWseokMHC8RMIFXMip9WWVHToN0vz";
    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().readTimeout(300, TimeUnit.SECONDS).build();

    /**
     * 获取百度OCR的access_token
     * @return token
     * @throws IOException 获取token异常
     */
    public static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return JSONObject.parseObject(response.body().string()).getString("access_token");
    }

    /**
     * 通用文字识别
     * @param file 文件
     * @return 结果
     * @throws IOException 识别异常
     */
    public static JSONObject ocrRegularScan(MultipartFile file) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String base64 = Base64.getEncoder().encodeToString(file.getBytes());
        base64 = URLEncoder.encode(base64, "utf-8");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "image="+base64);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        JSONObject jsonObject = JSONObject.parseObject(response.body().string());
        log.info("百度OCR返回结果：{}", jsonObject);
        return jsonObject;
    }
}
