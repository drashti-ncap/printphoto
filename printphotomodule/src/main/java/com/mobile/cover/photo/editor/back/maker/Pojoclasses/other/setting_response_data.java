package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.model.CancelReason;
import com.mobile.cover.photo.editor.back.maker.model.Country;
import com.mobile.cover.photo.editor.back.maker.model.Currency;

import java.util.List;

public class setting_response_data {
    @SerializedName("bulk_sms")
    @Expose
    private String bulkSms;
    @SerializedName("usd_price")
    @Expose
    private String usdPrice;
    @SerializedName("android_version")
    @Expose
    private String androidVersion;
    @SerializedName("android_forcefully_update")
    @Expose
    private String androidForcefullyUpdate;
    @SerializedName("ios_version")
    @Expose
    private String iosVersion;
    @SerializedName("ios_forcefully_update")
    @Expose
    private String iosForcefullyUpdate;
    @SerializedName("contact_email")
    @Expose
    private String contactEmail;
    @SerializedName("contact_mobile")
    @Expose
    private String contactMobile;
    @SerializedName("contact_address")
    @Expose
    private String contactAddress;
    @SerializedName("contact_support_timing")
    @Expose
    private String contactSupportTiming;
    @SerializedName("tutorial_link")
    @Expose
    private String tutorialLink;
    @SerializedName("tutorial_link_international")
    @Expose
    private String tutorial_link_international;
    @SerializedName("ProductType")
    @Expose
    private ProductType productType;
    @SerializedName("tickets_url")
    @Expose
    private String tickets_url;

    @SerializedName("gift_charge")
    @Expose
    private String gift_charge;

    @SerializedName("indian_sms_priority")
    @Expose
    private String indian_sms_priority;

    @SerializedName("international_sms_priority")
    @Expose
    private String international_sms_priority;

    @SerializedName("wa_india")
    @Expose
    private String wa_india;


    @SerializedName("wa_saudi")
    @Expose
    private String wa_saudi;

    @SerializedName("PREPAIDOFFER")
    @Expose
    private String PREPAIDOFFER;


    @SerializedName("resend_sms_priority")
    @Expose
    private String resend_sms_priority;


    @SerializedName("wa_home")
    @Expose
    private String wa_home;


    @SerializedName("wa_internatinal")
    @Expose
    private String wa_internatinal;

    @SerializedName("mall_enable_status")
    @Expose
    private Integer mall_status_enable;
    @SerializedName("currencies")
    @Expose
    private List<Currency> currencies = null;


    @SerializedName("countries")
    @Expose
    private List<Country> countries = null;


    @SerializedName("cancel_reasons")
    @Expose
    public List<CancelReason> cancel_reasons;

    public List<CancelReason> getCancel_reasons() {
        return cancel_reasons;
    }

    public void setCancel_reasons(List<CancelReason> cancel_reasons) {
        this.cancel_reasons = cancel_reasons;
    }

    public Integer getMall_status_enable() {
        return mall_status_enable;
    }

    public void setMall_status_enable(Integer mall_status_enable) {
        this.mall_status_enable = mall_status_enable;
    }

    public String getTutorial_link_international() {
        return tutorial_link_international;
    }

    public void setTutorial_link_international(String tutorial_link_international) {
        this.tutorial_link_international = tutorial_link_international;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public String getGift_charge() {
        return gift_charge;
    }

    public void setGift_charge(String gift_charge) {
        this.gift_charge = gift_charge;
    }

    public String getBulkSms() {
        return bulkSms;
    }

    public void setBulkSms(String bulkSms) {
        this.bulkSms = bulkSms;
    }

    public String getUsdPrice() {
        return usdPrice;
    }

    public void setUsdPrice(String usdPrice) {
        this.usdPrice = usdPrice;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getAndroidForcefullyUpdate() {
        return androidForcefullyUpdate;
    }

    public void setAndroidForcefullyUpdate(String androidForcefullyUpdate) {
        this.androidForcefullyUpdate = androidForcefullyUpdate;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getIosForcefullyUpdate() {
        return iosForcefullyUpdate;
    }

    public void setIosForcefullyUpdate(String iosForcefullyUpdate) {
        this.iosForcefullyUpdate = iosForcefullyUpdate;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactSupportTiming() {
        return contactSupportTiming;
    }

    public void setContactSupportTiming(String contactSupportTiming) {
        this.contactSupportTiming = contactSupportTiming;
    }

    public String getTutorialLink() {
        return tutorialLink;
    }

    public void setTutorialLink(String tutorialLink) {
        this.tutorialLink = tutorialLink;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getTickets_url() {
        return tickets_url;
    }

    public void setTickets_url(String tickets_url) {
        this.tickets_url = tickets_url;
    }


    public String getIndian_sms_priority() {
        return indian_sms_priority;
    }

    public void setIndian_sms_priority(String indian_sms_priority) {
        this.indian_sms_priority = indian_sms_priority;
    }

    public String getInternational_sms_priority() {
        return international_sms_priority;
    }

    public void setInternational_sms_priority(String international_sms_priority) {
        this.international_sms_priority = international_sms_priority;
    }


    public String getWa_india() {
        return wa_india;
    }

    public void setWa_india(String wa_india) {
        this.wa_india = wa_india;
    }

    public String getWa_saudi() {
        return wa_saudi;
    }

    public void setWa_saudi(String wa_saudi) {
        this.wa_saudi = wa_saudi;
    }

    public String getWa_internatinal() {
        return wa_internatinal;
    }

    public void setWa_internatinal(String wa_internatinal) {
        this.wa_internatinal = wa_internatinal;
    }

    public String getWa_home() {
        return wa_home;
    }

    public void setWa_home(String wa_home) {
        this.wa_home = wa_home;
    }

    public String getPREPAIDOFFER() {
        return PREPAIDOFFER;
    }

    public void setPREPAIDOFFER(String PREPAIDOFFER) {
        this.PREPAIDOFFER = PREPAIDOFFER;
    }

    public String getResend_sms_priority() {
        return resend_sms_priority;
    }

    public void setResend_sms_priority(String resend_sms_priority) {
        this.resend_sms_priority = resend_sms_priority;
    }
}