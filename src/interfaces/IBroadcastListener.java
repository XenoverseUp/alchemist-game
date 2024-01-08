package interfaces;

import java.util.HashMap;

import enums.BroadcastAction;

public interface IBroadcastListener {
    void onBroadcast(BroadcastAction action, HashMap<String, IDynamicTypeValue> payload);
}
