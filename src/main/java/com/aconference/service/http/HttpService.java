package com.aconference.service.http;

import com.aconference.service.google.entity.RefreshTokenRequest;
import com.aconference.service.google.entity.RefreshTokenResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class HttpService {

    private static final String REQUEST_CHARSET = "UTF-8";

    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, String> postJsonReponse(List<NameValuePair> params, String url) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params, REQUEST_CHARSET));
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return Collections.emptyMap();
        }
        String jsonResponse = StreamUtils.copyToString(entity.getContent(), Charset.forName(REQUEST_CHARSET));
        return objectMapper.readValue(jsonResponse, new TypeReference<Map<String, String>>(){});
    }


}
