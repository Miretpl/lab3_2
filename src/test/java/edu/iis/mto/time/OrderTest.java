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

    @Test public void OrderCreatedStateWithOneItem() {
        order.addItem(new OrderItem());
        assertThat(order.getOrderState(), is(Order.State.CREATED));
    }

    @Test public void OrderCreatedStateWithAddedItemAfterSubmit() {
        order.submit();
        order.addItem(new OrderItem());
        assertThat(order.getOrderState(), is(Order.State.CREATED));
    }

    @Test public void OrderSubmittedState() {
        order.submit();
        assertThat(order.getOrderState(), is(Order.State.SUBMITTED));
    }

    @Test public void OrderConfirmedState () {
        order.submit();
        order.confirm();

        assertThat(order.getOrderState(), is(Order.State.CONFIRMED));
    }
}
