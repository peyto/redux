package org.peyto.redux;

public interface Reducer<TState extends State, TAction extends Action> {

    /**
     * Defines class of actions, handled by this reducers
     * All reducers will be mapped by action.class, so only one reducer is allowed for each action class
     * By default, each Action returns Action.class, so it is a good practice to overload Action.getActionClass by
     * this.getClass method. In this case, you should also define reducer, which will handle specific action class.
     * @return
     */
    default Class getActionClass() {
        return Action.class;
    }

    default TState reduce(TState previousState, TAction action) {
        return previousState;
    }

}
