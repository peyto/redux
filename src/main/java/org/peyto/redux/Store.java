package org.peyto.redux;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Store
 */
public class Store<TState extends State<TState>> {

    private class DispatcherImpl implements Dispatcher {

        private Map<String, Object> getChangedStateProperties(State oldState, State newState) {
            Map<String, Object> oldMap = oldState.decompose();
            Map<String, Object> newMap = newState.decompose();
            if (!Sets.symmetricDifference(oldMap.keySet(), newMap.keySet()).isEmpty()) {
                throw new IllegalStateException(String.format("State changed it's structure, it should be like that, oldKeys=[%s], newKeys=[%s]", oldMap.keySet().toString(), newMap.keySet().toString()));
            }
            Map<String, Object> delta = new HashMap<>();
            for (String key : newMap.keySet()) {
                if (!oldMap.get(key).equals(newMap.get(key))) {
                    delta.put(key, newMap.get(key));
                }
            }
            return delta;
        }

        @Override
        public <T extends Action> void dispatch(T action) {
            dispatch(action, null);
        }

        @Override
        public synchronized <T extends Action> void dispatch(T action, StateChangeCallback callback) {
            Reducer<TState, T> reducer = (Reducer<TState, T>) reducers.get(action.getClass());
            if (reducer==null) {
                throw new IllegalArgumentException(String.format("Reducer not registered for action class [%s]", action.getClass().getSimpleName()));
            }
            TState newState = reducer.reduce(state, action);
            if (!state.equals(newState)) {
                Map<String, Object> changedStateProperties = getChangedStateProperties(state, newState);
                state = newState;
                // TODO Refactor! Should be in separate threads
                if (callback!=null) {
                    callback.onChange(changedStateProperties);
                }
            }
        }

    }

    private final Dispatcher dispatcher;
    private final Map<Class, Reducer<TState, ? extends Action>> reducers;
    private TState state;



    public Store(Reducer<TState, ? extends Action> reducer, TState initialState) {
        this(Collections.singletonList(reducer), initialState);
    }

    public Store(List<Reducer<TState, ? extends Action>> reducers, TState initialState) {
        this.dispatcher = new DispatcherImpl();
        this.reducers = reducers.stream().collect(Collectors.toMap(reducer -> reducer.getActionClass(), reducer -> reducer));
        this.state = initialState;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public TState getState() {
        return state;
    }
}
