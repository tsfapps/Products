package com.knotlink.salseman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelAsmList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_password")
    @Expose
    private String userPassword;
    @SerializedName("user_phone")
    @Expose
    private String userPhone;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_address")
    @Expose
    private String userAddress;
    @SerializedName("dl_no")
    @Expose
    private String dlNo;
    @SerializedName("user_doj")
    @Expose
    private String userDoj;
    @SerializedName("user_dob")
    @Expose
    private String userDob;
    @SerializedName("user_adhar_no")
    @Expose
    private String userAdharNo;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("user_asm_id")
    @Expose
    private String userAsmId;
    @SerializedName("user_sm_id")
    @Expose
    private String userSmId;
    @SerializedName("user_dm_id")
    @Expose
    private String userDmId;
    @SerializedName("user_imei_no")
    @Expose
    private String userImeiNo;
    @SerializedName("branch_id")
    @Expose
    private String branchId;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("sub_group_id")
    @Expose
    private String subGroupId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getDlNo() {
        return dlNo;
    }

    public void setDlNo(String dlNo) {
        this.dlNo = dlNo;
    }

    public String getUserDoj() {
        return userDoj;
    }

    public void setUserDoj(String userDoj) {
        this.userDoj = userDoj;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public String getUserAdharNo() {
        return userAdharNo;
    }

    public void setUserAdharNo(String userAdharNo) {
        this.userAdharNo = userAdharNo;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserAsmId() {
        return userAsmId;
    }

    public void setUserAsmId(String userAsmId) {
        this.userAsmId = userAsmId;
    }

    public String getUserSmId() {
        return userSmId;
    }

    public void setUserSmId(String userSmId) {
        this.userSmId = userSmId;
    }

    public String getUserDmId() {
        return userDmId;
    }

    public void setUserDmId(String userDmId) {
        this.userDmId = userDmId;
    }

    public String getUserImeiNo() {
        return userImeiNo;
    }

    public void setUserImeiNo(String userImeiNo) {
        this.userImeiNo = userImeiNo;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSubGroupId() {
        return subGroupId;
    }

    public void setSubGroupId(String subGroupId) {
        this.subGroupId = subGroupId;
    }

}
