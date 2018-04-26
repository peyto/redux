package org.peyto.redux;

import java.util.Map;

public interface StateChangeCallback {

    void onChange(Map<String, Object> properties);
}
