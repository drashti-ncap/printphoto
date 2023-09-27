package com.mobile.cover.photo.editor.back.maker.model;

import java.util.ArrayList;
import java.util.List;

public class variant_value {
    Boolean select;
    String variant_value;

    public variant_value(Boolean select, String variant_value) {
        this.select = select;
        this.variant_value = variant_value;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }

    public String getVariant_value() {
        return variant_value;
    }

    public void setVariant_value(String variant_value) {
        this.variant_value = variant_value;
    }
}
