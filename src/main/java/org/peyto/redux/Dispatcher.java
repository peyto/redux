package org.peyto.redux;

public interface Dispatcher {

    <T extends Action> void dispatch(T action);

    <T extends Action> void dispatch(T action, StateChangeCallback callback);

}
