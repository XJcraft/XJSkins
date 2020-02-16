package com.xj.skins.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class Https {

    public static byte[] request(String method, String url, byte[] data) throws IOException {
        HttpURLConnection connection = createUrlConnection(new URL(url));

        connection.setRequestMethod(method);
        if (method.equals("POST")) {
            connection.setRequestProperty("Content-Type", "application/json");
        }
        InputStream inputStream = null;
        try {
            if (data != null) {
                DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
                writer.write(data);
                writer.flush();
                Streams.closeQuietly(writer);
            }

            inputStream = connection.getInputStream();
            byte[] result = Streams.readAllAsBytes(inputStream);
            return result;
        } catch (IOException e) {
            inputStream = connection.getErrorStream();

            if (inputStream != null) {
                byte[] result = Streams.readAllAsBytes(inputStream);
                return result;
            } else {
                throw e;
            }
        }
    }

    protected static HttpURLConnection createUrlConnection(URL url)
            throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }
}