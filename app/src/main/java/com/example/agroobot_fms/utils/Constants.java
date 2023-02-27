package com.example.agroobot_fms.utils;

public final class Constants {

    public static final String USER_TOKEN = "user_token";

    // Endpoints
    public static final String BASE_URL = "http://103.65.236.175:2229";

    public static final String LOGIN_URL = "/api/login";
    public static final String LIST_MENU_URL = "/api/login/menu";

    public static final String GET_DATA_DASHBOARD_URL = "/api/dashboard/get-all";
    public static final String GET_ACTIVITY_DASHBOARD_URL = "/api/dashboard/get-activity";
    public static final String GET_PETANI_DASHBOARD_URL = "/api/dashboard/get-farmer";

    public static final String GET_ALL_DATA_PANEN_URL = "/api/datapanen/get-all";
    public static final String GET_ONE_DATA_PANEN_URL = "/api/datapanen/get-one";
    public static final String CREATE_DATA_PANEN_URL = "/api/datapanen/create";
    public static final String UPDATE_DATA_PANEN_URL = "/api/datapanen/update/{id}";
    public static final String DELETE_PANEN_URL = "/api/datapanen/delete/{id}";
    public static final String BATAL_AJUKAN_PANEN_URL = "/api/datapanen/draft/{id}";
    public static final String AJUKAN_PANEN_URL = "/api/datapanen/approval/{id}";

    public static final String GET_ONE_CULTIVATION_TASK_URL = "/api/cultivation-task/get-one";
    public static final String DROPDOWN_KONDISI_LAHAN_URL = "/api//cultivation-task/dropdownLandCondition";
    public static final String DROPDOWN_KONDISI_DAUN_URL = "/api//cultivation-task/dropdownLeafCondition";
    public static final String DROPDOWN_KONDISI_ANAKAN_URL = "/api//cultivation-task/dropdownPuppiesCondition";
    public static final String DROPDOWN_KONDISI_AIR_URL = "/api//cultivation-task/dropdownWateringCondition";
    public static final String DROPDOWN_KONDISI_BUTIR_URL = "/api//cultivation-task/dropdownGrainCondition";
    public static final String DROPDOWN_FILTER_PERIOD_URL = "/api/cultivation-task/dropdownPeriod";
    public static final String CREATE_DOCUMENTATION_URL = "/api/cultivation-task/create-documentation";
    public static final String CREATE_ACTIVITY_URL = "/api/cultivation-task/create-activity";
    public static final String CREATE_OBSERVATION_URL = "/api/cultivation-task/create-observation";
    public static final String CREATE_RATING_URL = "/api/cultivation-task/create-rating";
    public static final String UPDATE_DOCUMENTATION_URL = "/api/cultivation-task/update-documentation/{id}";
    public static final String UPDATE_RATING_URL = "/api/cultivation-task/update-rating/{id}";
    public static final String UPDATE_ACTIVITY_URL = "/api/cultivation-task/update-activity/{id}";
    public static final String UPDATE_OBSERVATION_URL = "/api/cultivation-task/update-observation/{id}";
    public static final String DELETE_DOCUMENTATION_URL = "/api/cultivation-task/delete-documentation/{id}";
    public static final String DELETE_RATING_URL = "/api/cultivation-task/delete-rating/{id}";
    public static final String DELETE_ACTIVITY_URL = "/api/cultivation-task/delete-activity/{id}";
    public static final String DELETE_OBSERVATION_URL = "/api/cultivation-task/delete-observation/{id}";
    public static final String DROPDOWN_FARMER_URL = "/api/land-commodity/dropdownFarmer";
    public static final String DROPDOWN_FILTER_LAHAN_URL = "/api/land-area/get-list-user";
    public static final String DROPDOWN_FILTER_PERIODE_URL = "/api/cultivation-task/dropdownPeriod";
    public static final String DROPDOWN_COMODITY_URL = "/api/commodity/dropdownCommodity";

    public static final String GET_ALL_BUDGET_PLAN_URL = "/api/budget-plan/get-all";
    public static final String CREATE_BUDGET_PLAN_URL = "/api/budget-plan/create";
    public static final String UPDATE_BUDGET_PLAN_URL = "/api/budget-plan/update/{id}";
    public static final String GET_ONE_BUDGET_PLAN_URL = "/api/budget-plan/get-one";
    public static final String DELETE_BUDGET_PLAN_URL = "/api/budget-plan/delete/{id}";

    public static final String GET_ALL_BUDGET_DETAIL_URL = "/api/budget-detail/get-all";
    public static final String GET_ONE_BUDGET_DETAIL_URL = "/api/budget-detail/get-one";
    public static final String DROPDOWN_ACTIVITY_URL = "/api/budget-detail/dropdownActivity";
    public static final String DROPDOWN_CATEGORY_URL = "/api/budget-detail/dropdownCategory";
    public static final String CREATE_BUDGET_DETAIL_URL = "/api/budget-detail/create";
    public static final String UPDATE_BUDGET_DETAIL_URL = "/api/budget-detail/update/{id}";
    public static final String DELETE_BUDGET_DETAIL_URL = "/api/budget-detail/delete/{id}";
    public static final String AJUKAN_BUDGET_DETAIL_URL = "/api/budget-detail/approval/{id}";
    public static final String BATAL_AJUKAN_BUDGET_DETAIL_URL = "/api/budget-detail/draft/{id}";
}
