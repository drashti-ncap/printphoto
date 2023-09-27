package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class cart_address_data_response {
    @SerializedName("status")
    @Expose
    private Integer status;


    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("display_cod_button")
    @Expose
    private Integer displayCodButton;
    @SerializedName("cod_available")
    @Expose
    private Integer codAvailable;
    @SerializedName("cod_message")
    @Expose
    private String codMessage;
    @SerializedName("currency_symbol")
    @Expose
    private String currency_symbol;
    @SerializedName("currency_code")
    @Expose
    private String currency_code;

    @SerializedName("display_gift_charge")
    @Expose
    private String display_gift_charge;

    @SerializedName("additional_shipping_charge")
    @Expose
    private int additional_shipping_charge;



    @SerializedName("order_details")
    @Expose
    private OrderDetails_response orderDetails;
    @SerializedName("enabled_payment_gateways")
    @Expose
    private List<String> enabledPaymentGateways = null;
    @SerializedName("extra")
    @Expose
    private Extra extra;

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getDisplay_gift_charge() {
        return display_gift_charge;
    }

    public void setDisplay_gift_charge(String display_gift_charge) {
        this.display_gift_charge = display_gift_charge;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getDisplayCodButton() {
        return displayCodButton;
    }

    public void setDisplayCodButton(Integer displayCodButton) {
        this.displayCodButton = displayCodButton;
    }

    public Integer getCodAvailable() {
        return codAvailable;
    }

    public void setCodAvailable(Integer codAvailable) {
        this.codAvailable = codAvailable;
    }

    public String getCodMessage() {
        return codMessage;
    }

    public int getAdditional_shipping_charge() {
        return additional_shipping_charge;
    }

    public void setAdditional_shipping_charge(int additional_shipping_charge) {
        this.additional_shipping_charge = additional_shipping_charge;
    }

    public void setCodMessage(String codMessage) {
        this.codMessage = codMessage;
    }

    public OrderDetails_response getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails_response orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<String> getEnabledPaymentGateways() {
        return enabledPaymentGateways;
    }

    public void setEnabledPaymentGateways(List<String> enabledPaymentGateways) {
        this.enabledPaymentGateways = enabledPaymentGateways;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }



}
