package com.example.attendance_v10.Retrofit;

import com.example.attendance_v10.ModelResponse.FaceRecResponse;

import retrofit2.http.Field;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("faceRecognition")
    Call<FaceRecResponse> faceResponse(
            @Field("recFace") String recFace
    );
}
