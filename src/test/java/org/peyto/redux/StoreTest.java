package org.peyto.redux;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mkhramov on 01.12.2017.
 */
public class StoreTest {

    private class StateImpl implements State<StateImpl> {

        private final Map<String, Object> internalState;

        public StateImpl(Map<String, Object> internalState) {
            this.internalState = new HashMap<>(internalState);
        }

        public StateImpl() {
            this.internalState = new HashMap<>();
        }

        @Override
        public Map<String, Object> decompose() {
            return internalState;
        }

        @Override
        public StateImpl getCopy() {
            return new StateImpl(internalState);
        }
    }

    Store<StateImpl> store;

    StateImpl initialState;

    @Before
    public void initStore() {
        Reducer<StateImpl, ? extends Action> reducer = Mockito.mock(Reducer.class);
        Map<String, Object> initialValues = new HashMap<>();
        initialValues.put("key1", "value1");
        initialValues.put("key2", "value2");
        initialState = new StateImpl(initialValues);
        store = new Store<StateImpl>(reducer, initialState);
    }

    @Test
    public void getDispatcher() throws Exception {

    }

    @Test
    public void getState() throws Exception {
        assertThat(store.getState().decompose()).hasSize(2).containsKeys("key1", "key2");

        // get state should return copy, not same object
        assertThat(store.getState()!=initialState);
    }

}