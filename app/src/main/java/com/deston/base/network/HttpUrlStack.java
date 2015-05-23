package com.deston.base.network;

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
        HttpURLConnection urlConnection = openConnection(request);
        setRequestParamsForConnection(urlConnection, request);
        int responseCode = urlConnection.getResponseCode();
        BasicHttpEntity entity = getEntityFromConnection(urlConnection);
        Map<String, String> header = convertHeaders(urlConnection);
        byte[] data = inputStreamToByteArray(entity.getContent());
        networkResponse = new NetworkResponse(data, responseCode, header);
        return networkResponse;
    }

    private byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int bufferSize = 4096;
        byte[] data = new byte[bufferSize];
        int count = -1;
        while ((count = inputStream.read(data)) != -1) {
            buffer.write(data, 0, bufferSize);
        }
        buffer.flush();
        return buffer.toByteArray();
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
        } catch (IOException e) {
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
