package com.example.agroobot_fms.api;

import com.example.agroobot_fms.model.list_menu.ListMenu;
import com.example.agroobot_fms.model.list_menu.ListMenuBody;
import com.example.agroobot_fms.model.login.LoginBody;
import com.example.agroobot_fms.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GetService {

    @POST("/api/login")
    Call<LoginResponse> loginAccount(@Body LoginBody loginBody);

    @POST("api/login/menu")
    Call<ListMenu> listMenu(@Body ListMenuBody loginBody);


}
