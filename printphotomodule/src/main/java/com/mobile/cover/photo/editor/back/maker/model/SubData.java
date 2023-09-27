package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubData {


    @SerializedName("id")
    int id;

    @SerializedName("category_id")
    String category_id;

    @SerializedName("company_name")
    String company_name;

    @SerializedName("created_at")
    String created_at;


    @SerializedName("updated_at")
    String updated_at;

    @SerializedName("deleted_at")
    String deleted_at;

    @SerializedName("model_detail")
    ArrayList<SubDataModelDetails> subDataModelDetailsArrayList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public ArrayList<SubDataModelDetails> getSubDataModelDetailsArrayList() {
        return subDataModelDetailsArrayList;
    }

    public void setSubDataModelDetailsArrayList(ArrayList<SubDataModelDetails> subDataModelDetailsArrayList) {
        this.subDataModelDetailsArrayList = subDataModelDetailsArrayList;
    }

}
