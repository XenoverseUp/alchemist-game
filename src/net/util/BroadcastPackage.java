package net.util;

import java.io.Serializable;
import java.util.HashMap;

import enums.BroadcastAction;
import interfaces.IDynamicTypeValue;

public class BroadcastPackage implements Serializable {
    private final BroadcastAction action;
    private HashMap<String, IDynamicTypeValue> payload = null;

    public BroadcastPackage(BroadcastAction action, HashMap<String, IDynamicTypeValue> payload) {
        this.action = action;
        this.payload = payload;
    }

    public BroadcastPackage(BroadcastAction action) {
        this.action = action;
    }

    public BroadcastAction getAction() {
        return action;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, IDynamicTypeValue> getPayload() {
        return payload != null ? (HashMap<String, IDynamicTypeValue>) payload.clone() : null;
    }

    public IDynamicTypeValue get(String field) {
        return this.payload.get(field);
    }
}
