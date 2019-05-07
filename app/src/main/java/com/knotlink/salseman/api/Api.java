package com.knotlink.salseman.api;

import com.knotlink.salseman.model.ModelAttendance;
import com.knotlink.salseman.model.ModelCash;
import com.knotlink.salseman.model.ModelColdCall;
import com.knotlink.salseman.model.ModelDistance;
import com.knotlink.salseman.model.ModelExpenses;
import com.knotlink.salseman.model.ModelFeedback;
import com.knotlink.salseman.model.ModelLead;
import com.knotlink.salseman.model.ModelMeeting;
import com.knotlink.salseman.model.ModelShopList;
import com.knotlink.salseman.model.ModelSpecialRequest;
import com.knotlink.salseman.model.ModelTask;
import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.model.report.ModelReportColdCall;
import com.knotlink.salseman.model.report.ModelReportDistance;
import com.knotlink.salseman.model.report.ModelReportExpenses;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.model.report.ModelReportMeeting;
import com.knotlink.salseman.model.report.ModelReportNewOrder;
import com.knotlink.salseman.model.report.ModelReportReceipt;
import com.knotlink.salseman.model.user.ModelUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api{
    @FormUrlEncoded
    @POST("api/api_login.php")
    Call<ModelUser> getUserDetail(
            @Field("user_phone") String phone_no,
            @Field("user_password") String user_password
    );

    @FormUrlEncoded
    @POST("api/api_attendance.php")
    Call<ModelAttendance> uploadLogin(
            @Field("user_id") String user_id,
            @Field("login_city") String login_city,
            @Field("login_pincode") String login_pincode,
            @Field("login_address") String login_address,
            @Field("login_latitude") String login_latitude,
            @Field("login_longitude") String login_longitude
    );
    @FormUrlEncoded
    @POST("api/api_attendance.php")
    Call<ModelAttendance> uploadLogout(
            @Field("user_id") String user_id,
            @Field("logout_city") String logout_city,
            @Field("logout_pincode") String logout_pincode,
            @Field("logout_address") String logout_address,
            @Field("logout_latitude") String logout_latitude,
            @Field("logout_longitude") String logout_longitude
    );
 @FormUrlEncoded
    @POST("api/api_shop_details.php")
    Call<List<ModelShopList>> getShopDetail(
            @Field("user_id") String user_id,
            @Field("present_day") String present_day
    );
 @FormUrlEncoded
    @POST("api/api_special_request.php")
    Call<ModelSpecialRequest> uploadSpecialRequest(
            @Field("special_request_type") String special_request_type,
            @Field("shop_id") String shop_id,
            @Field("remarks") String remarks
    );
 @FormUrlEncoded
    @POST("api/api_complaint.php")
    Call<ModelFeedback> uploadFeedback(
            @Field("shop_id") String shop_id,
            @Field("user_id") String user_id,
           // @Field("audio_url") String audio_url,
           // @Field("video_url") String video_url,
            @Field("image_url") String image_url,
            @Field("remarks") String remarks
    );

 @FormUrlEncoded
    @POST("api/api_distance_upload.php")
    Call<ModelDistance> uploadDistance(
//            starting_pincode
//
//
//
//ending_pincode
//
//ending_city
            @Field("user_id") String user_id,
            @Field("starting_km") String starting_km,
            @Field("starting_img") String starting_img,
            @Field("start_lat") String start_lat,
            @Field("start_long") String start_long,
            @Field("starting_pincode") String starting_pincode,
            @Field("starting_city") String starting_city,
            @Field("starting_address") String starting_address
    );

 @FormUrlEncoded
    @POST("api/api_cold_call.php")
    Call<ModelColdCall> uploadColdCall(
            @Field("user_id") String user_id,
            @Field("org_name") String org_name,
            @Field("contact_name") String contact_name,
            @Field("contact_number") String contact_number,
            @Field("address") String address,
            @Field("email") String email,
          //  @Field("city_id") String city_id,
            @Field("remarks") String remarks
//            @Field("latitude") String latitude,
//            @Field("longitude") String longitude
    );
 @FormUrlEncoded
    @POST("api/api_lead_generation.php")
    Call<ModelLead> uploadLead(

            @Field("user_id") String user_id,
            @Field("vendor_type") String vendor_type,
            @Field("org_name") String org_name,
            @Field("contact_name") String contact_name,
            @Field("contact_no") String contact_no,
            @Field("address") String address,
            @Field("email") String email,
          //  @Field("city_id") String city_id,
            @Field("remarks") String remarks,
            @Field("image") String image
//            @Field("latitude") String latitude,
//            @Field("longitude") String longitude
    );
    @FormUrlEncoded
    @POST("api/api_meeting.php")
    Call<ModelMeeting> uploadMeeting(
            @Field("user_id") String user_id,
            @Field("vendor_type") String vendor_type,
            @Field("org_name") String org_name,
            @Field("contact_name") String contact_name,
            @Field("contact_no") String contact_no,
            @Field("address") String address,
            @Field("email") String email,
          //  @Field("city_id") String city_id,
            @Field("remarks") String remarks
           // @Field("image") String image
//            @Field("latitude") String latitude,
//            @Field("longitude") String longitude
    );
    @FormUrlEncoded
    @POST("api/api_cashcollection.php")
    Call<ModelCash> uploadCashCollection(
            @Field("user_id") String user_id,
            @Field("cash_2000") String cash_2000,
            @Field("cash_500") String cash_500,
            @Field("cash_200") String cash_200,
            @Field("cash_100") String cash_100,
            @Field("cash_50") String cash_50,
            @Field("cash_20") String cash_20,
            @Field("cash_10") String cash_10,
            @Field("cash_5") String cash_5,
            @Field("cash_2") String cash_2,
            @Field("cash_1") String cash_1,
            @Field("total") String total
//            @Field("latitude") String latitude,
//            @Field("longitude") String longitude
    );
    @FormUrlEncoded
    @POST("api/api_cashcollection.php")
    Call<ModelExpenses> uploadExpenses(
            @Field("user_id") String user_id,
            @Field("breakfast") String breakfast,
          //  @Field("breakfast_image") String breakfast_image,
            @Field("lunch") String lunch,
          //  @Field("lunch_image") String lunch_image,
            @Field("dinner") String dinner,
          //  @Field("dinner_image") String dinner_image,
            @Field("mobile") String mobile,
           // @Field("mobile_image") String mobile_image,
            @Field("parking") String parking,
           // @Field("parking_image") String parking_image,
            @Field("road_toll_fee") String road_toll_fee,
           // @Field("road_toll_fee_image") String road_toll_fee_image,
            @Field("petty_cash") String petty_cash,
           // @Field("petty_cash_image") String petty_cash_image,
            @Field("others") String others,
           // @Field("others_image") String others_image,
            @Field("total") String total,
            @Field("remarks") String remarks
//            @Field("latitude") String latitude,
//            @Field("longitude") String longitude
    );

    @FormUrlEncoded
    @POST("api/api_assign_task.php")
    Call<List<ModelTask>> assignedTask(
            @Field("user_id") String user_id
    );
    @FormUrlEncoded
    @POST("api/api_activity_report.php")
    Call<List<ModelReportAttendance>> viewReportAttendance(
            @Field("user_id") String user_id,
            @Field("table") String table_name,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

 @FormUrlEncoded
    @POST("api/api_activity_report.php")
    Call<List<ModelReportDistance>> viewReportDistance(
            @Field("user_id") String user_id,
            @Field("table") String table_name,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

 @FormUrlEncoded
    @POST("api/api_activity_report.php")
    Call<List<ModelReportNewOrder>> viewReportNewOrder(
            @Field("user_id") String user_id,
            @Field("table") String table_name,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );
    @FormUrlEncoded
    @POST("api/api_activity_report.php")
    Call<List<ModelReportReceipt>> viewReportReceipt(
            @Field("user_id") String user_id,
            @Field("table") String table_name,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );
 @FormUrlEncoded
    @POST("api/api_activity_report.php")
    Call<List<ModelReportColdCall>> viewReportColdCall(
            @Field("user_id") String user_id,
            @Field("table") String table_name,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );
 @FormUrlEncoded
    @POST("api/api_activity_report.php")
    Call<List<ModelReportMeeting>> viewReportMeeting(
            @Field("user_id") String user_id,
            @Field("table") String table_name,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );
 @FormUrlEncoded
    @POST("api/api_activity_report.php")
    Call<List<ModelReportExpenses>> viewReportExpenses(
            @Field("user_id") String user_id,
            @Field("table") String table_name,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );
 @FormUrlEncoded
    @POST("api/api_activity_report.php")
    Call<List<ModelReportLeadGeneration>> viewReportLead(
            @Field("user_id") String user_id,
            @Field("table") String table_name,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );
}
