package org.peyto.redux;

import com.google.common.collect.Sets;

import java.util.Map;

/**
 * Should be immutable!
 */
public interface State<TState extends State> {

    Map<String, Object> decompose();

    TState getCopy();

    default boolean equals(State state) {
        Map<String, Object> oldMap = this.decompose();
        Map<String, Object> newMap = state.decompose();
        if (!Sets.symmetricDifference(oldMap.keySet(), newMap.keySet()).isEmpty()) {
            throw new IllegalStateException(String.format("State changed it's structure, it should be like that, oldKeys=[%s], newKeys=[%s]", oldMap.keySet().toString(), newMap.keySet().toString()));
        }
        for (String key : newMap.keySet()) {
            if (!oldMap.get(key).equals(newMap.get(key))) {
                return false;
            }
        }
        return true;
    }
}
