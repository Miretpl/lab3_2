package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderTest {

    private Order order;

    @Before public void setUp() throws Exception {
        order = new Order();
    }

    @Test public void OrderCreatedState() {
        assertThat(order.getOrderState(), is(Order.State.CREATED));
    }

    @Test public void OrderSubmitedState() {
        order.submit();
        assertThat(order.getOrderState(), is(Order.State.SUBMITTED));
    }
}
