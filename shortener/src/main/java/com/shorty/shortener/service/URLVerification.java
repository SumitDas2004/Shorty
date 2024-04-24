package com.shorty.shortener.service;

import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class URLVerification {
    public boolean checkIfValidURL(String request) throws Exception {
        try {
            URL url = new URL(request);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            int responseCode = huc.getResponseCode();

            return responseCode >= 200 && responseCode < 300;
        }catch (Exception e){
            throw new Exception("Invalid URL provided.");
        }
    }
}
