package com.example.customerAdmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TokenResponse {
    @Expose
    @SerializedName("access_token")
    String accessToken;
}
