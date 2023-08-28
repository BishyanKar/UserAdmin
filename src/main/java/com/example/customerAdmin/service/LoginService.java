package com.example.customerAdmin.service;

import com.example.customerAdmin.AuthTokenManager;
import com.example.customerAdmin.model.TokenResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LoginService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthTokenManager authTokenManager;

    Gson gson = new Gson();

    public boolean authorize() {
        if (authTokenManager.getAuthToken() != null) {
            return true;
        }
        return false;
    }

    public void authenticateAndSetAuthToken(String email, String password) {

        String loginUri = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("login_id", email);
        requestBody.put("password", password);

        String response = restTemplate.postForObject(loginUri, new HttpEntity<>(requestBody), String.class);
        TokenResponse tokenResponse = gson.fromJson(response, TokenResponse.class);
        authTokenManager.setAuthToken(Objects.requireNonNull(tokenResponse).getAccessToken());
    }
}
