package com.entrylevelcoder.entrylevelcoder.services;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AdzunaService {
//    @Value("${adzunaId}")
//    public String appId;

//    @Value("${adzuna}")
//    public String appKey;
    public String returnJSON() {
//        String URL = String.format("https://api.adzuna.com/v1/api/jobs/us/search/1?app_id=%s&app_key=%s", appId, appKey);
        String URL = "https://api.adzuna.com/v1/api/jobs/us/search/1?app_id=f5c81ba0&app_key=e98078033fa9f579bd2fda7e9d301718%09&results_per_page=100&what=software%20web&what_exclude=senior%20sr%20admin%20manager%20coordinator%20lead%20advisory%20advisor%20provider%20director%20instructor%20ii%20iii%20iiii%20II%20III%20IIII%202%203%204%20mid-level%20barista&max_days_old=90&sort_by=date&content-type=application/json";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();

        System.out.println(request);

        String res = null;
        try {
            Response response = client.newCall(request).execute();
            res = response.body().string();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;

    }
}
