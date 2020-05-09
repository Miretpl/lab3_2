package edu.iis.mto.time;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {

    private Order order;
    private Instant instant;

    @Before public void setUp() throws Exception {
        order = new Order();
        instant = new Instant();
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
        order.confirm(instant);

        assertThat(order.getOrderState(), is(Order.State.CONFIRMED));
    }

    @Test public void OrderConfirmThrowOrderExpiredException () {
        order.submit();
        assertThrows(OrderExpiredException.class, () ->order.confirm(instant.plus(Duration.standardDays(2))));
    }
}
