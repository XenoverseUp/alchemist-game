package net.util;

import interfaces.IDynamicTypeValue;

public class StringTypeValue implements IDynamicTypeValue {
    private final String value;

    public StringTypeValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String valueDescription() {
        return getValue();
    }
}
