package com.knotlink.salseman.api;

import com.knotlink.salseman.model.ModelAttendance;
import com.knotlink.salseman.model.ModelCash;
import com.knotlink.salseman.model.ModelColdCall;
import com.knotlink.salseman.model.ModelExpenseList;
import com.knotlink.salseman.model.ModelGetLocaion;
import com.knotlink.salseman.model.distance.ModelDistancePrevious;
import com.knotlink.salseman.model.report.ModelReportVehicle;
import com.knotlink.salseman.model.task.ModelNewCustomer;
import com.knotlink.salseman.model.ModelRoute;
import com.knotlink.salseman.model.ModelSalesMan;
import com.knotlink.salseman.model.task.ModelTaskDecline;
import com.knotlink.salseman.model.ModelVisit;
import com.knotlink.salseman.model.distance.ModelDistance;
import com.knotlink.salseman.model.ModelExpenses;
import com.knotlink.salseman.model.ModelFeedback;
import com.knotlink.salseman.model.ModelInvoice;
import com.knotlink.salseman.model.ModelLead;
import com.knotlink.salseman.model.ModelMeeting;
import com.knotlink.salseman.model.ModelNewOrder;
import com.knotlink.salseman.model.ModelShopList;
import com.knotlink.salseman.model.ModelSpecialRequest;
import com.knotlink.salseman.model.report.ModelReportCashCollection;
import com.knotlink.salseman.model.task.ModelTask;
import com.knotlink.salseman.model.ModelVehicleList;
import com.knotlink.salseman.model.distance.ModelDistanceUpdate;
import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.model.report.ModelReportColdCall;
import com.knotlink.salseman.model.report.ModelReportDistance;
import com.knotlink.salseman.model.report.ModelReportExpenses;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.model.report.ModelReportMeeting;
import com.knotlink.salseman.model.report.ModelReportNewOrder;
import com.knotlink.salseman.model.report.ModelReportReceipt;
import com.knotlink.salseman.model.task.ModelTaskReschedule;
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
            @Field("user_type") String user_type,
            @Field("present_day") String present_day
    );

 @FormUrlEncoded
    @POST("api/api_shop_not_visit.php")
    Call<ModelVisit> getShopNotVisit(
            @Field("remarks") String remarks,
            @Field("user_id") String user_id,
            @Field("shop_id") String shop_id
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
            @Field("image_url") String image_url,
            @Field("remarks") String remarks
    );

 @FormUrlEncoded
    @POST("api/api_distance_upload.php")
    Call<ModelDistance> uploadDistance(
            @Field("user_id") String user_id,
            @Field("starting_km") String starting_km,
            @Field("starting_img") String starting_img,
            @Field("start_lat") String start_lat,
            @Field("start_long") String start_long,
            @Field("starting_pincode") String starting_pincode,
            @Field("starting_city") String starting_city,
            @Field("starting_address") String starting_address,
            @Field("vehicle_no") String vehicle_no
    );
 @FormUrlEncoded
    @POST("api/api_distance_upload_update.php")
    Call<ModelDistanceUpdate> uploadDistanceEnding(
            @Field("user_id") String user_id,
            @Field("ending_km") String ending_km,
            @Field("ending_img") String ending_img,
            @Field("end_lat") String end_lat,
            @Field("end_long") String end_long,
            @Field("ending_pincode") String ending_pincode,
            @Field("ending_city") String ending_city,
            @Field("ending_address") String ending_address,
            @Field("vehicle_no") String vehicle_no

    );

    @FormUrlEncoded
    @POST("api/api_previous_day_distance.php")
    Call<ModelDistancePrevious> getDistancePrevious(
            @Field("vehicle_no") String vehicle_no
    );
    @POST("api/api_vehicle.php")
    Call<List<ModelVehicleList>> vehicleList(
    );


 @FormUrlEncoded
    @POST("api/api_new_order.php")
    Call<ModelNewOrder> uploadNewOrder(
            @Field("user_id") String user_id,
            @Field("shop_id") String shop_id,
            @Field("date_of_delivery") String date_of_delivery,
            @Field("remarks") String remarks,
            @Field("signature") String signature,
            @Field("ordered_image") String ordered_image,
          //@Field("product_id") String product_id
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );
 @FormUrlEncoded
    @POST("api/api_cold_call.php")
    Call<ModelColdCall> uploadColdCall(
            @Field("user_id") String user_id,
            @Field("route_id") String route_id,
            @Field("org_name") String org_name,
            @Field("contact_name") String contact_name,
            @Field("contact_no") String contact_no,
            @Field("landline_no") String landline_no,
            @Field("address") String address,
            @Field("email") String email,
            @Field("city") String city,
            @Field("nxt_meeting_date") String nxt_meeting_date,
            @Field("remarks") String remarks
    );
 @FormUrlEncoded
    @POST("api/api_lead_generation.php")
    Call<ModelLead> uploadLead(
         @Field("user_id") String user_id,
         @Field("route_id") String route_id,
         @Field("vendor_type") String vendor_type,
         @Field("org_name") String org_name,
         @Field("contact_name") String contact_name,
         @Field("contact_no") String contact_no,
         @Field("address") String address,
         @Field("email") String email,
         @Field("city") String city,
         @Field("pincode") String pincode,
         @Field("nxt_meeting_date") String nxt_meeting_date,
         @Field("whatsapp_no") String whatsapp_no,
         @Field("landline_no") String landline_no,
         @Field("remarks") String remarks,
         @Field("image") String image,
         @Field("latitude") String latitude,
         @Field("longitude") String longitude
    );
 @FormUrlEncoded
    @POST("api/api_convert_to_customer.php")
    Call<ModelNewCustomer> newCustomer(

         @Field("task_id") String task_id,
         @Field("user_id") String user_id,
         @Field("route_id") String route_id,
         @Field("shop_name") String shop_name,
         @Field("contact_name") String contact_name,
         @Field("contact_no") String contact_no,
         @Field("email") String email,
         @Field("shop_address") String shop_address,
         @Field("city") String city,
         @Field("pincode") String pincode,
         @Field("gst_no") String gst_no,
         @Field("landline_no") String landline_no,
         @Field("whatsapp_no") String whatsapp_no,
         @Field("remarks") String remarks,
         @Field("image1") String image1,
         @Field("image2") String image2,
         @Field("image3") String image3,
         @Field("latitude") String latitude,
         @Field("longitude") String longitude
    );
    @FormUrlEncoded
    @POST("api/api_meeting.php")
    Call<ModelMeeting> uploadMeeting(
            @Field("user_id") String user_id,
            @Field("route_id") String route_id,
            @Field("vendor_type") String vendor_type,
            @Field("org_name") String org_name,
            @Field("contact_name") String contact_name,
            @Field("contact_no") String contact_no,
            @Field("address") String address,
            @Field("email") String email,
            @Field("city") String city,
            @Field("pincode") String pincode,
            @Field("nxt_meeting_date") String nxt_meeting_date,
            @Field("whatsapp_no") String whatsapp_no,
            @Field("landline_no") String landline_no,
            @Field("remarks") String remarks,
            @Field("image") String image,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
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
            @Field("total") String total,
            @Field("total_amount") String total_amount,
            @Field("no_cheque") String no_cheque,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("pin_code") String pin_code,
            @Field("address") String address
    );
    @FormUrlEncoded
    @POST("api/api_assign_task.php")
    Call<List<ModelTask>> assignedTask(
            @Field("user_id") String user_id
    );
    @FormUrlEncoded
    @POST("api/api_decline.php")
    Call<ModelTaskDecline> declineTask(
            @Field("task_id") String task_id,
            @Field("remarks") String remarks
    );
    @FormUrlEncoded
    @POST("api/api_user_route.php")
    Call<List<ModelRoute>> userRouteList(
            @Field("user_id") String user_id
    );
    @POST("api/api_route.php")
    Call<List<ModelRoute>> allRouteList();

    @FormUrlEncoded
    @POST("api/api_asm_salesman.php")
    Call<List<ModelSalesMan>> salesAsmManList(
            @Field("asm_id") String asm_id
    );
// @POST("api/api_salesman.php")
//    Call<List<ModelSalesMan>> salesAsmManList();

    @FormUrlEncoded
    @POST("api/api_assign_task_update.php")
    Call<ModelTaskReschedule> assignedTaskReschedule(
            @Field("task_id") String task_id,
            @Field("user_remarks") String user_remarks,
            @Field("nxt_meeting_date") String nxt_meeting_date
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
    @FormUrlEncoded
    @POST("api/api_activity_report.php")
    Call<List<ModelReportCashCollection>> viewReportCash(
            @Field("user_id") String user_id,
            @Field("table") String table_name,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );
    @FormUrlEncoded
    @POST("api/api_activity_report.php")
    Call<List<ModelReportVehicle>> viewReportVehicle(
            @Field("user_id") String user_id,
            @Field("table") String table_name,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );
    @FormUrlEncoded
    @POST("api/api_invoice_fetch.php")
    Call<ModelInvoice>  viewInvoice(
            @Field("shop_id") String shop_id,
            @Field("invoice_no") String invoice_no
    );

    @POST("api/api_expense_type.php")
    Call<List<ModelExpenseList>>  expenseList(
    );

    @FormUrlEncoded
    @POST("api/api_expenses.php")
    Call<ModelExpenses> uploadExpenses(
            @Field("user_id") String user_id,
            @Field("expense_type") String expense_type,
            @Field("amount") String amount,
            @Field("remarks") String remarks,
            @Field("image") String image
//          @Field("latitude") String latitude,
//          @Field("longitude") String longitude
    );
    @FormUrlEncoded
    @POST("api/api_getlocation.php")
    Call<ModelGetLocaion> updateLocation(
            @Field("shop_id") String shop_id,
            @Field("shop_address") String shop_address,
            @Field("city") String city,
            @Field("pincode") String pincode,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );

}
