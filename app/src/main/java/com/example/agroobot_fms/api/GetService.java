package com.example.agroobot_fms.api;

import com.example.agroobot_fms.model.get_all_data_panen.DataPanen;
import com.example.agroobot_fms.model.get_one.GetOne;
import com.example.agroobot_fms.model.get_one.GetOneBody;
import com.example.agroobot_fms.model.list_menu.ListMenu;
import com.example.agroobot_fms.model.list_menu.ListMenuBody;
import com.example.agroobot_fms.model.login.LoginBody;
import com.example.agroobot_fms.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetService {

    @POST("/api/login")
    Call<LoginResponse> loginAccount(@Body LoginBody loginBody);

    @POST("/api/login/menu")
    Call<ListMenu> listMenu(@Body ListMenuBody loginBody);

    @GET("/api/datapanen/get-all")
    Call<DataPanen> getAllDataPanen();


    @GET("/api/datapanen/get-one")
    Call<DataPanen> getOneDataPanen(@Query("id_seq") int idSeq);


}
