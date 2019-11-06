package com.knotlink.salseman.model.notice;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.knotlink.salseman.R;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.DateUtils;

public class ModelNoticeComplain{


    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("route_name")
    @Expose
    private String routeName;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("manager")
    @Expose
    private String manager;
    @SerializedName("sm")
    @Expose
    private String sm;
    @SerializedName("dm")
    @Expose
    private String dm;
    @SerializedName("asm")
    @Expose
    private String asm;
    @SerializedName("attended_by")
    @Expose
    private String attendedBy;
    @SerializedName("CURRENT_DATE()")
    @Expose
    private String cURRENTDATE;
    @SerializedName("total_days")
    @Expose
    private String totalDays;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getDatetime() {
        return DateUtils.convertFormatDateTime(datetime);
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemarks() {
        String strRem = "Remarks : ";
        return strRem+remarks; }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImageUrl() {
        String imgUrl = Constant.URL_IMG_COMPLAIN;
        return imgUrl+imageUrl;}

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getAsm() {
        return asm;
    }

    public void setAsm(String asm) {
        this.asm = asm;
    }

    public String getAttendedBy() {
        String strAtt = "Attended by : ";
        return strAtt+attendedBy;
    }

    public void setAttendedBy(String attendedBy) {
        this.attendedBy = attendedBy;
    }

    public String getCURRENTDATE() {
        return cURRENTDATE;
    }

    public void setCURRENTDATE(String cURRENTDATE) {
        this.cURRENTDATE = cURRENTDATE;
    }

    public String getTotalDays() {
        String strDay = "Days : ";
        return strDay+totalDays;
    }
    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }


    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView tImageView, String tImgUrl){
        Glide.with(tImageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                .circleCrop())
                .load(tImgUrl)
                .placeholder(R.drawable.main_logo)
                .into(tImageView);
    }

}
