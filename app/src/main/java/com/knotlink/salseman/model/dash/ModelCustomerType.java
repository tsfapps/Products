package com.knotlink.salseman.model.dash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCustomerType {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer_type")
    @Expose
    private String customerType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
