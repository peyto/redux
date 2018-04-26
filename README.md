# redux
Java implementation for Redux. Core concepts: Store, State, Action, Reducer

The idea is taken from **React-redux** (https://redux.js.org/basics/usage-with-react)

The core concept of Redux are: **Store**, **State**, **Action**, **Reducers**.

Store is the central component which accomodates all logic. It contains global State, which is changed be Actions.
Whenever some action happens in the system, it is dispatched by store, which finds an approciate Reducer. Reducer is the function that transforms "input state" to "output state" based on "Action".

To be able to user Java Redux you need:
1. Create Store
2. Pass to Store list of Reducers, each reducer handles one Action type
3. Pass to Store initial State
4. Whenever you need to change the state of the system, create an action, and user Store.Dispatcher to dispatch action.

## Immutability

If you need to track changes of the state, implement State as immutable and pass StateChangeCallback to dispatch.
In that case you can implement State comparison very efficient, but the application will generate more garbage.
If you don't need to notify clients regarding state changes, it makes sense to make State mutable, since usually redux is used in single-thread model (actions go one by one)

## Contacts

For any question and suggestions feel free to email: peyto.org@gmail.com
