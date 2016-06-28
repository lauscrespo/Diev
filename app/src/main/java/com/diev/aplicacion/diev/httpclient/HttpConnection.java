package com.diev.aplicacion.diev.httpclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpConnection {
    public static String sendRequest(RequestConfiguration config) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            con = (HttpURLConnection) (new URL(config.getRequestUrl()).openConnection());
            con.setRequestMethod(config.getType().toString());
            con.setConnectTimeout(2000);
            con.setDoInput(true);
            con.setDoOutput(true);
            if (config.getType() == MethodType.POST) {
                OutputStream os = con.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(config.getParams());

                writer.flush();
                writer.close();
                os.close();
            }
            StringBuffer buffer = new StringBuffer();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null)
                    buffer.append(line + "\r\n");

                is.close();
                con.disconnect();
            }
            return buffer.toString();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
                Log.e("HtttpConection","Error al cerrar: " + t.toString());
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
                Log.e("HtttpConection","Error al desconectar: " + t.toString());
            }
        }
        return null;
    }
}
