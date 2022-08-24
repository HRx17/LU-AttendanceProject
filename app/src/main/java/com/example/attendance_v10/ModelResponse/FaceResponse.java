package com.example.attendance_v10.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class FaceResponse {

    String message;

    public FaceResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
