package org.peyto.redux;

public interface Action {

    String DEFAULT = "default";

    default String getActionSubType() {
        return DEFAULT;
    }

}
