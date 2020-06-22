package com.konwhy.crm.enums;

public enum ImageProperty {
    IMAGE_TYPE_PNG("PNG"),IMAGE_TYPE_JPG("JPG"),WEB_VALID_IMAGE_PREFIX("OFFSET");

    //成员变量
    private String value;
    ImageProperty(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
