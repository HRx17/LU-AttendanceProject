package com.example.attendance_v10.Retrofit;

import com.example.attendance_v10.ModelResponse.FaceResponse;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("stud")
    Call<FaceResponse> faceResponse(
            @Field("link") String link,
            @Field("name") String name
    );

}
