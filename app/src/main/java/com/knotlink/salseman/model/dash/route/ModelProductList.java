package com.knotlink.salseman.model.dash.route;

public class ModelProductList {

    private boolean isChecked;
    private String productA;
    private String getProductB;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getProductA() {
        return productA;
    }

    public void setProductA(String productA) {
        this.productA = productA;
    }

    public String getGetProductB() {
        return getProductB;
    }

    public void setGetProductB(String getProductB) {
        this.getProductB = getProductB;
    }
}
