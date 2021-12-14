package com.demain.platform.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import lombok.Data;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class IPUtil {

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }


    /**
     * 根据IP获取实际请求地址
     * @param ip
     * @return
     */
    public static IpInfo getRealAddressByIp(String ip){

        String url="https://ip.taobao.com/outGetIpInfo?accessKey=alibaba-inc&ip="+ip;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);

        IpInfo ipInfo = null;

        try {
            Response response = call.execute();
            String responseData = response.body().string();
            Gson gson = new Gson();
                JSONObject jsonObject = JSON.parseObject(responseData);
                //  code的值的含义为，0：成功，1：服务器异常，2：请求参数异常，3：服务器繁忙，4：个人qps超出。
                if(jsonObject.get("code").equals(0)){
                    ipInfo = gson.fromJson(jsonObject.get("data").toString(), IpInfo.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ipInfo;
    }


    @Data
    public class IpInfo{
        private String country_id;  // CN
        private String country;     // 中国
        private String region;      // 广东
        private String city_id;     // 440300
        private String city;        // 深圳
        private String queryIp;     // 210.12.24.50
    }

}
