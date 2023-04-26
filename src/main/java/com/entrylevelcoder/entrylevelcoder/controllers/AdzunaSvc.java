package com.entrylevelcoder.entrylevelcoder.controllers;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class AdzunaSvc {

        @Value("${adzuna_app_id}")
        public String appId;

        @Value("${adzuna}")
        public String appKey;

        public String returnJSON() {
            String URL = String.format("https://api.adzuna.com/v1/api/jobs/gb/search/1?app_id=%s&app_key=%s", appId, appKey);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(URL)
                    .build();

            String res = null;
            try {
                Response response = client.newCall(request).execute();
                res = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return res;

        }

    }


