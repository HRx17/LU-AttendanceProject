package com.example.attendance_v10.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class FaceResponse {

    String response;

    public FaceResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
