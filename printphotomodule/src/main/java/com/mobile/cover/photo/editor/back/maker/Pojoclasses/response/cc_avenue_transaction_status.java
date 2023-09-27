package com.mobile.cover.photo.editor.back.maker.Pojoclasses.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class cc_avenue_transaction_status {
    @SerializedName("transaction_success")
    @Expose
    private Integer transactionSuccess;

    public Integer getTransactionSuccess() {
        return transactionSuccess;
    }

    public void setTransactionSuccess(Integer transactionSuccess) {
        this.transactionSuccess = transactionSuccess;
    }
}
