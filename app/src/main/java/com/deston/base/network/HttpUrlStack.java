package com.deston.base.network;

import android.util.Log;
import com.deston.base.CommonUtil;
import com.deston.base.Constants;
import org.apache.http.entity.BasicHttpEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUrlStack {
    public NetworkResponse performRequest(HttpRequest request) throws IOException {
        NetworkResponse networkResponse = null;
        Log.i(Constants.TAG_COMM, "HttpUrlStack performRequest: " + "begin openConnection");
        HttpURLConnection urlConnection = openConnection(request);
        setRequestParamsForConnection(urlConnection, request);
        int responseCode = urlConnection.getResponseCode();
        Log.i(Constants.TAG_COMM, "HttpUrlStack performRequest: get responseCode : " + responseCode);
        InputStream inputStream = urlConnection.getInputStream();
        Map<String, String> header = convertHeaders(urlConnection);
        networkResponse = parseResponseByType(request.getType(), inputStream, responseCode, header);
        return networkResponse;
    }
    private NetworkResponse parseResponseByType(Class<?> type, InputStream inputStream, int responseCode, Map<String, String> header) {
        if (type == String.class) {
            return new NetworkResponse<String>(CommonUtil.inputSreamToString(inputStream), responseCode, header);
        }
        return null;
    }

    private Map<String, String> convertHeaders(HttpURLConnection urlConnection) {
        Map<String, String> header = new HashMap<String, String>();
        for (Map.Entry<String, List<String>> entry : urlConnection.getHeaderFields().entrySet()) {
            header.put(entry.getKey(), entry.getValue().get(0));
        }
        return header;
    }

    private BasicHttpEntity getEntityFromConnection(HttpURLConnection urlConnection) {
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream inputStream;
        try {
            inputStream = urlConnection.getInputStream();
            Log.i(Constants.TAG_COMM, "HttpUrlStack getEntityFromConnection: String = " + CommonUtil.inputSreamToString(inputStream));
        } catch (IOException e) {
            Log.i(Constants.TAG_COMM, "HttpUrlStack getEntityFromConnection: catch IOException= " + e);
            inputStream = urlConnection.getErrorStream();
        }
        entity.setContent(inputStream);
        entity.setContentLength(urlConnection.getContentLength());
        entity.setContentType(urlConnection.getContentType());
        entity.setContentEncoding(urlConnection.getContentEncoding());
        return entity;
    }

    private void setRequestParamsForConnection(HttpURLConnection urlConnection, HttpRequest request) throws ProtocolException {
        switch (request.getMethod()) {
            case HttpRequest.Method.GET:
                urlConnection.setRequestMethod("GET");
                break;
            case HttpRequest.Method.POST:
                urlConnection.setRequestMethod("POST");
                break;
            case HttpRequest.Method.DELETE:
                urlConnection.setRequestMethod("DELETE");
                break;
        }
    }

    private HttpURLConnection openConnection(HttpRequest request) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(request.getUrl()).openConnection();
        urlConnection.setConnectTimeout(request.getTimeOuts());
        urlConnection.setReadTimeout(request.getTimeOuts());
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        return urlConnection;
    }
}
