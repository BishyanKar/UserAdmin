package com.example.customerAdmin.service;

import com.example.customerAdmin.AuthTokenManager;
import com.example.customerAdmin.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final String baseUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AuthTokenManager authTokenManager;

    Gson gson = new Gson();

    public void invalidateAuthToken() {
        authTokenManager.setAuthToken(null);
    }

    public List<User> getAllUsers() {
        String getAllUsersUri = baseUrl.concat("?cmd=get_customer_list");
        HttpHeaders headers = new HttpHeaders();
        if (authTokenManager.getAuthToken() == null) {
            return null;
        }
        headers.add("Authorization", "Bearer ".concat(authTokenManager.getAuthToken()));

        ResponseEntity<String> response = restTemplate.exchange(getAllUsersUri, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return null;
        }
        return gson.fromJson(response.getBody(), new TypeToken<List<User>>(){}.getType());
    }

    public User getUserById(String uuid) {
        String getAllUsersUri = baseUrl.concat("?cmd=get_customer_list");
        HttpHeaders headers = new HttpHeaders();
        if (authTokenManager.getAuthToken() == null) {
            return null;
        }
        headers.add("Authorization", "Bearer ".concat(authTokenManager.getAuthToken()));

        ResponseEntity<String> response = restTemplate.exchange(getAllUsersUri, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return null;
        }
        List<User> userList = gson.fromJson(response.getBody(), new TypeToken<List<User>>(){}.getType());
        return Objects.requireNonNull(userList).stream()
                .filter(user -> user.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public String addUser(User user) {
        String getAllUsersUri = baseUrl.concat("?cmd=create");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer ".concat(authTokenManager.getAuthToken()));

        String json = gson.toJson(user);
        Map<String, Object> requestBody = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());

        ResponseEntity<String> response = restTemplate.exchange(getAllUsersUri, HttpMethod.POST, new HttpEntity<>(requestBody, headers), String.class);
        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return null;
        }
        return response.getBody();
    }

    public String updateUser(User user) {
        String getAllUsersUri = baseUrl.concat("?cmd=update&uuid="+user.getUuid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer ".concat(authTokenManager.getAuthToken()));

        String json = gson.toJson(user);
        Map<String, Object> requestBody = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());

        ResponseEntity<String> response = restTemplate.exchange(getAllUsersUri, HttpMethod.POST, new HttpEntity<>(requestBody, headers), String.class);
        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return null;
        }
        return response.getBody();
    }

    public String deleteUser(String uuid) {
        String getAllUsersUri = baseUrl.concat("?cmd=delete&uuid="+uuid);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer ".concat(authTokenManager.getAuthToken()));

        ResponseEntity<String> response = restTemplate.exchange(getAllUsersUri, HttpMethod.POST, new HttpEntity<>(headers), String.class);
        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return null;
        }
        return response.getBody();
    }
}
