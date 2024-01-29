package net.util;

import java.io.Serializable;

import interfaces.IDynamicTypeValue;


public class DynamicTypeValue<T> implements IDynamicTypeValue, Serializable {
    private final T value;

    public DynamicTypeValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    @Override
    public String valueDescription() {
        return value.toString();
    }
}
