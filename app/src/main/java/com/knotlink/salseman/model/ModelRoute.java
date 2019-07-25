package com.knotlink.salseman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRoute {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("route_name")
    @Expose
    private String routeName;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("salesman_id")
    @Expose
    private String salesmanId;
    @SerializedName("asm_id")
    @Expose
    private Object asmId;
    @SerializedName("dispatcher_id")
    @Expose
    private String dispatcherId;
    @SerializedName("dm_id")
    @Expose
    private Object dmId;
    @SerializedName("day")
    @Expose
    private String day;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public Object getAsmId() {
        return asmId;
    }

    public void setAsmId(Object asmId) {
        this.asmId = asmId;
    }

    public String getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(String dispatcherId) {
        this.dispatcherId = dispatcherId;
    }

    public Object getDmId() {
        return dmId;
    }

    public void setDmId(Object dmId) {
        this.dmId = dmId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
