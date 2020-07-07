package com.aniu.aspectjeasy;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aniu.aspectjeasy.stats.NetworkStatsManager;
import com.aniu.aspectjeasy.stats.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        NetworkStatsManager.setUser(new User("110", "police"));

        button.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {

            new Thread(
                    new Runnable() {
                      @Override
                      public void run() {
                        try {
                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            String content =
                                get("http://192.168.1.2:8080/get.json", "linchangjian");
                                Log.i(TAG, content);
                          }
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                      }
                    })
                .start();
          }
        });
    }

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    String get(String url, String json)throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
//                .addInterceptor(new GzipRequestInterceptor())
                .build();

                Request request = new Request.Builder()
                .url(url)
                .get().header("Accept-Encoding","gzip")
                .build();
        Response response = client.newCall(request).execute();
        String string = response.body().string();
        return string;
    }
    String post(String url, String json) throws IOException {

        OkHttpClient client = null;
// 配置一些信息进入OkHttpClient
        client = new OkHttpClient().newBuilder()

                .addInterceptor(new GzipRequestInterceptor())
                .build();
//        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(url)
                .get()
//                .post(requestBodyWithContentLength(body))
                .build();
        Response response = client.newCall(request).execute();
        return response.toString();
    }


    public static String uncompressString(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str
                .getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toString();
    }

    private RequestBody requestBodyWithContentLength(final RequestBody requestBody) throws IOException {
        final Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
        } catch (IOException e) {
            throw new IOException("Unable to copy RequestBody");
        }
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return requestBody.contentType();
            }

            @Override
            public long contentLength() {
                return buffer.size();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(ByteString.read(buffer.inputStream(), (int) buffer.size()));
            }
        };
    }
}


