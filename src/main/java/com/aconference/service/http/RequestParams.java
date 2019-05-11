package com.aconference.service.http;

import com.aconference.service.google.entity.RefreshTokenRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RequestParams {

    private Set<NameValuePair> params = new HashSet<>();

    public RequestParams addParameter(String name, String value) {
        params.add(new BasicNameValuePair(name, value));
        return this;
    }

    public Set<NameValuePair> getParams() {
        return params;
    }

    public List<NameValuePair> toList() {
        return new ArrayList<>(params);
    }
}
