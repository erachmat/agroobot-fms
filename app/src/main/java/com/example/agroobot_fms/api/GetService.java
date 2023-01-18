package com.example.agroobot_fms.api;

import static com.example.agroobot_fms.utils.Constants.CREATE_ACTIVITY_URL;
import static com.example.agroobot_fms.utils.Constants.CREATE_BUDGET_DETAIL_URL;
import static com.example.agroobot_fms.utils.Constants.CREATE_BUDGET_PLAN_URL;
import static com.example.agroobot_fms.utils.Constants.CREATE_DATA_PANEN_URL;
import static com.example.agroobot_fms.utils.Constants.CREATE_DOCUMENTATION_URL;
import static com.example.agroobot_fms.utils.Constants.CREATE_OBSERVATION_URL;
import static com.example.agroobot_fms.utils.Constants.CREATE_RATING_URL;
import static com.example.agroobot_fms.utils.Constants.DELETE_ACTIVITY_URL;
import static com.example.agroobot_fms.utils.Constants.DELETE_BUDGET_DETAIL_URL;
import static com.example.agroobot_fms.utils.Constants.DELETE_BUDGET_PLAN_URL;
import static com.example.agroobot_fms.utils.Constants.DELETE_DOCUMENTATION_URL;
import static com.example.agroobot_fms.utils.Constants.DELETE_OBSERVATION_URL;
import static com.example.agroobot_fms.utils.Constants.DELETE_PANEN_URL;
import static com.example.agroobot_fms.utils.Constants.DELETE_RATING_URL;
import static com.example.agroobot_fms.utils.Constants.DROPDOWN_ACTIVITY_URL;
import static com.example.agroobot_fms.utils.Constants.DROPDOWN_CATEGORY_URL;
import static com.example.agroobot_fms.utils.Constants.DROPDOWN_FILTER_PERIOD_URL;
import static com.example.agroobot_fms.utils.Constants.DROPDOWN_KONDISI_AIR_URL;
import static com.example.agroobot_fms.utils.Constants.DROPDOWN_KONDISI_ANAKAN_URL;
import static com.example.agroobot_fms.utils.Constants.DROPDOWN_KONDISI_BUTIR_URL;
import static com.example.agroobot_fms.utils.Constants.DROPDOWN_KONDISI_DAUN_URL;
import static com.example.agroobot_fms.utils.Constants.DROPDOWN_KONDISI_LAHAN_URL;
import static com.example.agroobot_fms.utils.Constants.GET_ALL_BUDGET_DETAIL_URL;
import static com.example.agroobot_fms.utils.Constants.GET_ALL_BUDGET_PLAN_URL;
import static com.example.agroobot_fms.utils.Constants.GET_ALL_DATA_PANEN_URL;
import static com.example.agroobot_fms.utils.Constants.GET_ONE_BUDGET_DETAIL_URL;
import static com.example.agroobot_fms.utils.Constants.GET_ONE_BUDGET_PLAN_URL;
import static com.example.agroobot_fms.utils.Constants.GET_ONE_CULTIVATION_TASK_URL;
import static com.example.agroobot_fms.utils.Constants.GET_ONE_DATA_PANEN_URL;
import static com.example.agroobot_fms.utils.Constants.LIST_MENU_URL;
import static com.example.agroobot_fms.utils.Constants.LOGIN_URL;
import static com.example.agroobot_fms.utils.Constants.UPDATE_ACTIVITY_URL;
import static com.example.agroobot_fms.utils.Constants.UPDATE_BUDGET_DETAIL_URL;
import static com.example.agroobot_fms.utils.Constants.UPDATE_BUDGET_PLAN_URL;
import static com.example.agroobot_fms.utils.Constants.UPDATE_DATA_PANEN_URL;
import static com.example.agroobot_fms.utils.Constants.UPDATE_DOCUMENTATION_URL;
import static com.example.agroobot_fms.utils.Constants.UPDATE_OBSERVATION_URL;
import static com.example.agroobot_fms.utils.Constants.UPDATE_RATING_URL;

import com.example.agroobot_fms.model.DeleteBudgetPlan;
import com.example.agroobot_fms.model.create_activity.CreateActivityBody;
import com.example.agroobot_fms.model.create_activity.CreateActivityResponse;
import com.example.agroobot_fms.model.create_budget_detail.CreateBudgetDetail;
import com.example.agroobot_fms.model.create_budget_plan.CreateBudgetPlan;
import com.example.agroobot_fms.model.create_budget_plan.CreateBudgetPlanBody;
import com.example.agroobot_fms.model.create_data_panen.CreateDataPanen;
import com.example.agroobot_fms.model.create_documentation.CreateDocumentation;
import com.example.agroobot_fms.model.create_observation.CreateObservation;
import com.example.agroobot_fms.model.create_observation.CreateObservationBody;
import com.example.agroobot_fms.model.create_rating.CreateRatingBody;
import com.example.agroobot_fms.model.create_rating.CreateRatingResponse;
import com.example.agroobot_fms.model.delete_activity.DeleteActivityBody;
import com.example.agroobot_fms.model.delete_activity.DeleteActivityResponse;
import com.example.agroobot_fms.model.delete_budget_detail.DeleteBudgetDetail;
import com.example.agroobot_fms.model.delete_data_panen.DeleteDataPanen;
import com.example.agroobot_fms.model.delete_documentation.DeleteDocumentation;
import com.example.agroobot_fms.model.delete_documentation.DeleteDocumentationBody;
import com.example.agroobot_fms.model.delete_observation.DeleteObservationBody;
import com.example.agroobot_fms.model.delete_observation.DeleteObservationResponse;
import com.example.agroobot_fms.model.delete_rating.DeleteRatingBody;
import com.example.agroobot_fms.model.delete_rating.DeleteRatingResponse;
import com.example.agroobot_fms.model.dropdown_activity.DropdownActivity;
import com.example.agroobot_fms.model.dropdown_category.DropdownCategory;
import com.example.agroobot_fms.model.dropdown_filter_period.FilterPeriod;
import com.example.agroobot_fms.model.dropdown_kondisi_air.KondisiAir;
import com.example.agroobot_fms.model.dropdown_kondisi_anakan.KondisiAnakan;
import com.example.agroobot_fms.model.dropdown_kondisi_butir.KondisiButir;
import com.example.agroobot_fms.model.dropdown_kondisi_daun.KondisiDaun;
import com.example.agroobot_fms.model.dropdown_kondisi_lahan.KondisiLahan;
import com.example.agroobot_fms.model.get_all_budget_detail.GetAllBudgetDetail;
import com.example.agroobot_fms.model.get_all_budget_plan.GetAllBudgetPlan;
import com.example.agroobot_fms.model.get_all_data_panen.DataPanen;
import com.example.agroobot_fms.model.get_one.GetOne;
import com.example.agroobot_fms.model.get_one.GetOneBody;
import com.example.agroobot_fms.model.get_one_budget_detail.GetOneBudgetDetail;
import com.example.agroobot_fms.model.get_one_budget_plan.GetOneBudgetPlan;
import com.example.agroobot_fms.model.list_menu.ListMenu;
import com.example.agroobot_fms.model.list_menu.ListMenuBody;
import com.example.agroobot_fms.model.login.LoginBody;
import com.example.agroobot_fms.model.login.LoginResponse;
import com.example.agroobot_fms.model.update_activity.UpdateActivityBody;
import com.example.agroobot_fms.model.update_activity.UpdateActivityResponse;
import com.example.agroobot_fms.model.update_budget_detail.UpdateBudgetDetail;
import com.example.agroobot_fms.model.update_budget_plan.UpdateBudgetPlan;
import com.example.agroobot_fms.model.update_budget_plan.UpdateBudgetPlanBody;
import com.example.agroobot_fms.model.update_data_panen.UpdateDataPanen;
import com.example.agroobot_fms.model.update_documentation.UpdateDocumentationResponse;
import com.example.agroobot_fms.model.update_observation.UpdateObservationBody;
import com.example.agroobot_fms.model.update_observation.UpdateObservationResponse;
import com.example.agroobot_fms.model.update_rating.UpdateRatingBody;
import com.example.agroobot_fms.model.update_rating.UpdateRatingResponse;

import java.util.Map;
import java.util.concurrent.Callable;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetService {

    @POST(LOGIN_URL)
    Call<LoginResponse> loginAccount(@Body LoginBody loginBody);

    @POST(LIST_MENU_URL)
    Call<ListMenu> listMenu(@Body ListMenuBody loginBody);

    @GET(GET_ALL_DATA_PANEN_URL)
    Call<DataPanen> getAllDataPanen(@Header("Authorization") String token);

    @GET(GET_ONE_DATA_PANEN_URL)
    Call<DataPanen> getOneDataPanen(
            @Header("Authorization") String token,
            @Query("id_seq") int idSeq);

    @FormUrlEncoded
    @POST(CREATE_DATA_PANEN_URL)
    Call<CreateDataPanen> createDataPanen(
            @Header("Authorization") String token,
            @Field("commodity_name_var") String commodityNameVar,
            @Field("land_code_var") String landCodeVar,
            @Field("period_plant_txt") String periodPlantText,
            @Field("harvest_flo") String harvestFlo,
            @Field("harvest_on_dte") String harvestOnDte,
            @Field("harvest_drying_flo") String harvestDryingflo,
            @Field("harvest_drying_dte") String harvestDryingDte,
            @Field("harvest_milling_flo") String harvestMillingFlo,
            @Field("harvest_milling_dte") String harvestMilingDte,
            @Field("created_by_var") String createdByVar);

    @Multipart
    @PATCH(UPDATE_DATA_PANEN_URL)
    Call<UpdateDataPanen> updateDataPanen(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Part("commodity_name_var") RequestBody commodityNameVar,
            @Part("land_code_var") RequestBody landCodeVar,
            @Part("period_plant_txt") RequestBody periodPlantText,
            @Part("harvest_flo") RequestBody harvestFlo,
            @Part("harvest_on_dte") RequestBody harvestOnDte,
            @Part("harvest_drying_flo") RequestBody harvestDryingflo,
            @Part("harvest_drying_dte") RequestBody harvestDryingDte,
            @Part("harvest_milling_flo") RequestBody harvestMillingFlo,
            @Part("harvest_milling_dte") RequestBody harvestMilingDte,
            @Part("updated_by_var") RequestBody updatedByVar);


    @DELETE(DELETE_PANEN_URL)
    Call<DeleteDataPanen> deleteDataPanen(
            @Path("id") int id,
            @Header("Authorization") String token);

    @POST(GET_ONE_CULTIVATION_TASK_URL)
    Call<GetOne> getOneCultivation(
            @Header("Authorization") String token,
            @Body GetOneBody getOneBody);

    @GET(DROPDOWN_KONDISI_LAHAN_URL)
    Call<KondisiLahan> dropdownKondisiLahan(@Header("Authorization") String token);

    @GET(DROPDOWN_KONDISI_DAUN_URL)
    Call<KondisiDaun> dropdownKondisiDaun(@Header("Authorization") String token);

    @GET(DROPDOWN_KONDISI_ANAKAN_URL)
    Call<KondisiAnakan> dropdownKondisiAnakan(@Header("Authorization") String token);

    @GET(DROPDOWN_KONDISI_AIR_URL)
    Call<KondisiAir> dropdownKondisiAir(@Header("Authorization") String token);

    @GET(DROPDOWN_KONDISI_BUTIR_URL)
    Call<KondisiButir> dropdownKondisiButir(@Header("Authorization") String token);

    @GET(DROPDOWN_FILTER_PERIOD_URL)
    Call<FilterPeriod> dropdownFilterPeriod(
            @Header("Authorization") String token,
            @Query("land_code_var") int landCodeVar);

    @Multipart
    @POST(CREATE_DOCUMENTATION_URL)
    Call<CreateDocumentation> createDocumentation(
            @Header("Authorization") String token,
            @Part("user_id_int") RequestBody userIdInt,
            @Part("land_code_var") RequestBody landCodeVar,
            @Part("period_plant_txt") RequestBody periodPlantText,
            @Part("time_calender_dte") RequestBody timeCalendarDte,
            @Part("created_by_var") RequestBody createdByVar);

    @POST(CREATE_ACTIVITY_URL)
    Call<CreateActivityResponse> createActivity(
            @Header("Authorization") String token,
            @Body CreateActivityBody createActivityBody);

    @POST(CREATE_OBSERVATION_URL)
    Call<CreateObservation> createObservation(
            @Header("Authorization") String token,
            @Body CreateObservationBody createObservationBody);

    @POST(CREATE_RATING_URL)
    Call<CreateRatingResponse> createRating(
            @Header("Authorization") String token,
            @Body CreateRatingBody createRatingBody);

    @Multipart
    @PATCH(UPDATE_DOCUMENTATION_URL)
    Call<UpdateDocumentationResponse> updateDocumetation(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Part("user_id_int") RequestBody userIdInt,
            @Part("land_code_var") RequestBody landCodeVar,
            @Part("period_plant_txt") RequestBody periodPlantText,
            @Part("time_calender_dte") RequestBody timeCalendarDte,
            @Part("created_by_var") RequestBody createdByVar);

    @PATCH(UPDATE_RATING_URL)
    Call<UpdateRatingResponse> updateRating(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Body UpdateRatingBody updateRatingBody);

    @PATCH(UPDATE_ACTIVITY_URL)
    Call<UpdateActivityResponse> updateActivity(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Body UpdateActivityBody updateActivityBody);

    @PATCH(UPDATE_OBSERVATION_URL)
    Call<UpdateObservationResponse> updateObservation(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Body UpdateObservationBody updateObservationBody);

    @PATCH(DELETE_RATING_URL)
    Call<DeleteRatingResponse> deleteRating(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Body DeleteRatingBody deleteRatingBody);

    @PATCH(DELETE_DOCUMENTATION_URL)
    Call<DeleteDocumentation> deleteDocumentation(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Body DeleteDocumentationBody deleteDocumentationBody);

    @PATCH(DELETE_OBSERVATION_URL)
    Call<DeleteObservationResponse> deleteObservation(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Body DeleteObservationBody deleteObservationBody);

    @PATCH(DELETE_ACTIVITY_URL)
    Call<DeleteActivityResponse> deleteActivity(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Body DeleteActivityBody deleteActivityBody);

    @GET(GET_ALL_BUDGET_PLAN_URL)
    Call<GetAllBudgetPlan> getAllBudgetPlan(@Header("Authorization") String token);

    @POST(CREATE_BUDGET_PLAN_URL)
    Call<CreateBudgetPlan> createBudgetPlan(
            @Header("Authorization") String token,
            @Body CreateBudgetPlanBody createBudgetPlanBody);

    @PATCH(UPDATE_BUDGET_PLAN_URL)
    Call<UpdateBudgetPlan> updateBudgetPlan(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Body UpdateBudgetPlanBody updateBudgetPlanBody);

    @GET(GET_ONE_BUDGET_PLAN_URL)
    Call<GetOneBudgetPlan> getOneBudgetPlan(
            @Header("Authorization") String token,
            @Query("id_seq") int id_seq);

    @DELETE(DELETE_BUDGET_PLAN_URL)
    Call<DeleteBudgetPlan> deleteBudgetPlan(
            @Path("id") int id,
            @Header("Authorization") String token);

    @GET(GET_ALL_BUDGET_DETAIL_URL)
    Call<GetAllBudgetDetail> getAllBudgetDetail(@Header("Authorization") String token);

    @GET(GET_ONE_BUDGET_DETAIL_URL)
    Call<GetOneBudgetDetail> getOneBudgetDetail(
            @Header("Authorization") String token,
            @Query("id_seq") int idSeq);

    @GET(DROPDOWN_ACTIVITY_URL)
    Call<DropdownActivity> dropdownActivity(
            @Header("Authorization") String token,
            @Query("category_var") String categoryVar);

    @GET(DROPDOWN_CATEGORY_URL)
    Call<DropdownCategory> dropdownCategory(@Header("Authorization") String token);

    @Multipart
    @POST(CREATE_BUDGET_DETAIL_URL)
    Call<CreateBudgetDetail> createBudgetDetail(
            @Header("Authorization") String token,
            @Part("budget_id_int") RequestBody budgetIdInt,
            @Part("activity_txt") RequestBody activityTxt,
            @Part("category_var") RequestBody categoryVar,
            @Part("area_var") RequestBody areaVar,
            @Part("quantity_var") RequestBody quantityVar,
            @Part("satuan_var") RequestBody satuanVar,
            @Part("price_var") RequestBody priceVar,
            @Part("created_by_var") RequestBody createByVar);

    @Multipart
    @PATCH(UPDATE_BUDGET_DETAIL_URL)
    Call<UpdateBudgetDetail> updateBudgetDetail(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Part("budget_id_int") RequestBody budgetIdInt,
            @Part("activity_txt") RequestBody activityTxt,
            @Part("category_var") RequestBody categoryVar,
            @Part("area_var") RequestBody areaVar,
            @Part("quantity_var") RequestBody quantityVar,
            @Part("satuan_var") RequestBody satuanVar,
            @Part("price_var") RequestBody priceVar,
            @Part("created_by_var") RequestBody createByVar);

    @DELETE(DELETE_BUDGET_DETAIL_URL)
    Call<DeleteBudgetDetail> deleteBudgetDetail(
            @Path("id") int id,
            @Header("Authorization") String token);
}
