package com.example.cyaisdk.client;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.example.cyaisdk.model.ChartRequest;
import com.example.cyaisdk.model.ChartResponse;

import java.util.HashMap;

/**
 * @author 86147
 * create  6/7/2023 上午8:41
 */
public class AiClient {

    private String accessKey;

    private String secretKey;

    private static final String CHAT_URL="http://localhost:8090/api/chat";

    private static final String CHART_URL="http://localhost:8090/api/chat";

    public AiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public HttpRequest createConnection(String content,String url) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("nonce", RandomUtil.randomNumbers(4));
        headers.put("accesskey",accessKey);
        headers.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        headers.put("sign",sign(accessKey,secretKey));

        return  HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .addHeaders(headers)
                .body(content);
    }

    public String getAiAnswer(String question){
        HashMap<String, String> map = new HashMap<>();
        map.put("question",question);
        System.out.println(JSONUtil.toJsonStr(map));
        HttpRequest connection = createConnection(JSONUtil.toJsonStr(map), CHAT_URL);
        HttpResponse response = connection.execute();
        return response.body();

    }

    public ChartResponse getAiChartAnswer(ChartRequest request){
        HttpRequest connection = createConnection(JSONUtil.toJsonStr(request), CHART_URL);
        HttpResponse response = connection.execute();
        String body = response.body();
        TypeReference<ChartResponse> typeRef = new TypeReference<ChartResponse>() {
        };
        return (ChartResponse) JSONUtil.toBean(body, typeRef, false);
    }



    public static String sign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        return md5.digestHex(content);
    }

    public static void main(String[] args) {
        AiClient aiClient = new AiClient("f32d484133d89f0099a0c1d413825725", "408ce7487fd2bd118e38cebd39fdb239");
        String hello = aiClient.getAiAnswer("hello");
        System.out.println(hello);
    }

}
