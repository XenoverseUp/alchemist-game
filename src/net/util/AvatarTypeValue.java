package net.util;

import enums.Avatar;
import interfaces.IDynamicTypeValue;

public class AvatarTypeValue implements IDynamicTypeValue {
    private final Avatar value;

    public AvatarTypeValue(Avatar value) {
        this.value = value;
    }

    public Avatar getValue() {
        return value;
    }

    @Override
    public String valueDescription() {
        return this.value.toString();
    }

    
}
